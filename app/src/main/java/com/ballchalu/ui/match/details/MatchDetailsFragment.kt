package com.ballchalu.ui.match.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.R
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentMatchDetailsBinding
import com.ballchalu.mqtt.MqttConnection
import com.ballchalu.ui.create_bet.CreateBetFragment
import com.ballchalu.ui.match.details.adapter.EndingDigitAdapter
import com.ballchalu.ui.match.details.adapter.SessionAdapter
import com.ballchalu.ui.match.details.my_bets.MyBetsFragment
import com.ballchalu.ui.winners.WinnersFragment
import com.ballchalu.utils.StringUtils
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.domain.create_bet.CreateBetRes
import com.ccpp.shared.domain.create_bet.CreateSessionBetRes
import com.ccpp.shared.domain.match_details.Market
import com.ccpp.shared.domain.match_details.Runner
import com.ccpp.shared.domain.match_details.Session
import com.ccpp.shared.domain.match_details.SessionsItem
import com.ccpp.shared.util.ColorUtils
import com.ccpp.shared.util.ConstantsBase
import com.ccpp.shared.util.viewModelProvider
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import javax.inject.Inject

class MatchDetailsFragment : BaseFragment(), CreateBetFragment.OnBetResponseSuccessListener {

    private val handler: Handler = Handler()
    private var snackBar: Snackbar? = null
    private var sessionAdapter: SessionAdapter? = null
    private var endingDigitAdapter: EndingDigitAdapter? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var mqttConnection: MqttConnection

    private lateinit var binding: FragmentMatchDetailsBinding
    private lateinit var viewModel: MatchDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMatchDetailsBinding.inflate(inflater).apply {
            lifecycleOwner = this@MatchDetailsFragment
            model = viewModel
            tvContest.text = viewModel.title
        }
        if (viewModel.isDeclared)
            setWinnerListener()
        registerReceiver()

        return binding.root
    }

    private fun setWinnerListener() {
        binding.ivWinner.setOnClickListener {
            childFragmentManager.run {
                if (findFragmentByTag(WinnersFragment::class.java.simpleName) == null) {
                    beginTransaction().remove(viewModel.myBetFragment)
                        .commitAllowingStateLoss()
                    beginTransaction()
                        .add(
                            R.id.llMyBetContainer,
                            viewModel.winnersFragment,
                            WinnersFragment::class.java.simpleName
                        )
                        .commitAllowingStateLoss()
                    binding.llMatchContainer.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModelProvider(viewModelFactory)
        arguments?.let {
            viewModel.providerId = it.getInt(ConstantsBase.KEY_PROVIDER_ID)
            viewModel.contestsId = it.getInt(ConstantsBase.KEY_CONTESTS_ID)
            viewModel.contestsMatchId = it.getInt(ConstantsBase.KEY_CONTESTS_MATCH_ID)
            viewModel.matchId = it.getInt(ConstantsBase.KEY_MATCH_ID)
            viewModel.title = it.getString(ConstantsBase.KEY_TITLE)
            viewModel.isDeclared = it.getBoolean(ConstantsBase.KEY_DECLARED)
        }
    }

    private fun registerReceiver() {
        requireContext().registerReceiver(
            networkStateReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        requireContext().registerReceiver(
            viewModel.scoreUpdate,
            IntentFilter(ConstantsBase.ACTION_PUB_NUB)
        )
        requireContext().registerReceiver(
            viewModel.oddsReceiver,
            IntentFilter(ConstantsBase.ODDS_UPDATES)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireContext().unregisterReceiver(viewModel.scoreUpdate)
        requireContext().unregisterReceiver(viewModel.oddsReceiver)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListeners()
        initSessionAdapterAdapter()
        initEndingDigitAdapterAdapter()

        viewModel.matchResult.observe(viewLifecycleOwner, EventObserver { response ->
            binding.tvMatchTeam1.text = response?.match?.team1
            binding.tvMatchTeam2.text = response?.match?.team2
            binding.tvBetStatus.text = response?.match?.heroicCommentary?.event ?: ""
            val secondsDelayed = 2000
            handler.postDelayed(runnable, secondsDelayed.toLong())
        })
        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            if (binding.pullRefreshLayout.isRefreshing)
                binding.pullRefreshLayout.isRefreshing = false
        })

        viewModel.sessionEvent.observe(viewLifecycleOwner, EventObserver { sessionList ->
            binding.llSessionSection.visibility =
                if (sessionList?.isNotEmpty() == true) View.VISIBLE else View.GONE
            binding.rvSession.visibility =
                if (sessionList?.isNotEmpty() == true) View.VISIBLE else View.GONE
            sessionAdapter?.setItemList(sessionList)
        })

        viewModel.failure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
        })

        viewModel.winnerMarketEvent.observe(viewLifecycleOwner, EventObserver { market ->
            binding.tvMarketType.text = market?.betfairMarketType
            binding.tvMatchTeam1.text = market?.runners?.get(0)?.runner?.betfairRunnerName
            binding.tvMatchTeam2.text = market?.runners?.get(1)?.runner?.betfairRunnerName
        })

        viewModel.evenOddMarketEvent.observe(viewLifecycleOwner, EventObserver {
            setEvenOddData(it)
        })

        viewModel.endingDigitMarketEvent.observe(viewLifecycleOwner, EventObserver { market ->
            binding.tvEndingDigitTitle.text = market.betfairMarketType
            binding.llEndingDigitSection.visibility =
                if (market.runners?.isNotEmpty() == true) View.VISIBLE else View.GONE
            endingDigitAdapter?.setItemList(market.runners, market.status, market.id)
        })

        viewModel.betStatusEvent.observe(viewLifecycleOwner, EventObserver {
            handler.removeCallbacks(runnable)
            if (viewModel.isFPBT(it)) {
                val secondsDelayed = 2000
                handler.postDelayed(runnable, secondsDelayed.toLong())
            }
            StringUtils.setEvents(it, binding.tvBetStatus)
        })
        viewModel.updateSessionEvent.observe(viewLifecycleOwner, EventObserver {
            updateSession(it)
        })

        viewModel.updateScoreEvent.observe(viewLifecycleOwner, EventObserver { score ->
            score?.batteamname?.let { binding.tvBatTeamName.text = it }
            score?.bwlteamname?.let { binding.tvBallTeamName.text = it }

            score?.batteamdesc?.let { binding.tvBatTeamScore.text = it }
            binding.tvBallTeamScore.text =
                if (score?.bwlteamdesc.isNullOrEmpty()) "Bowling..." else score?.bwlteamdesc
        })

        viewModel.batTeamBhaavEvent.observe(viewLifecycleOwner, EventObserver { runners ->
            binding.tvTeam1Lay.text = if (runners?.canBack == true) runners.back else ""
            binding.tvTeam1Back.text = if (runners?.canLay == true) runners.lay else ""
            binding.tvTeam1Lay.isClickable = (runners?.canBack == true)
            binding.tvTeam1Back.isClickable = (runners?.canLay == true)
            runners?.betfairRunnerName?.let {
                viewModel.batTeamRunner = runners
            }
        })

        viewModel.bwlTeamBhaavEvent.observe(viewLifecycleOwner, EventObserver { runners ->
            binding.tvTeam2Lay.text = if (runners?.canBack == true) runners.back else ""
            binding.tvTeam2Back.text = if (runners?.canLay == true) runners.lay else ""
            binding.tvTeam2Lay.isClickable = (runners?.canBack == true)
            binding.tvTeam2Back.isClickable = (runners?.canLay == true)
            runners?.betfairRunnerName?.let {
                viewModel.bwlTeamRunner = runners
            }
        })

        viewModel.updateEndingDigitDataEvent.observe(
            viewLifecycleOwner,
            EventObserver { market ->
                binding.llEndingDigitSection.visibility =
                    if (market.runners?.isNotEmpty() == true) View.VISIBLE else View.GONE
                market.runners?.forEach {
                    it.runner = Runner(back = it.B, canBack = it.canBack, marketId = market.id)
                }
                endingDigitAdapter?.updateEndingDigit(market.runners, market.status)
            })

        viewModel.updateEvenOddDataEvent.observe(viewLifecycleOwner, EventObserver { market ->
            binding.frameEvenOdd.visibility =
                if (market.runners?.isNotEmpty() == true) View.VISIBLE else View.GONE
            val marketStatus = market.status?.equals(ConstantsBase.open, true) == true
            binding.tvEvenOddType.text = market.betfairMarketType

            market.runners?.get(0)?.let {
                it.marketId = market.id
                binding.tvOddEvenBack1.text = if (it.canBack && marketStatus) it.B else ""
                binding.tvOddEvenBack1.isClickable = (it.canBack && marketStatus)
                viewModel.evenMarket = it
            }
            market.runners?.get(1)?.let {
                it.marketId = market.id
                binding.tvOddEvenBack2.text = if (it.canBack && marketStatus) it.L else ""
                binding.tvOddEvenBack2.isClickable = (it.canBack && marketStatus)
                viewModel.evenMarket = it
            }
        })

        viewModel.positionMatchWinnerObserver.observe(viewLifecycleOwner, EventObserver {
            binding.tvMatchWinnerPosition1.apply {
                text = it?.batTeamRunner?.runner() ?: ""
                setTextColor(it?.batTeamRunner?.color() ?: Color.GREEN)
            }
            binding.tvMatchWinnerPosition2.apply {
                text = it?.bwlTeamRunner?.runner() ?: ""
                setTextColor(it?.bwlTeamRunner?.color() ?: Color.GREEN)
            }
        })

        viewModel.positionEvenOddObserver.observe(viewLifecycleOwner, EventObserver {
            binding.tvEvenPosition.apply {
                text = it?.even?.runner()
                setTextColor(it?.even?.color() ?: Color.GREEN)
            }
            binding.tvOddPosition.apply {
                text = it?.odd?.runner()
                setTextColor(it?.odd?.color() ?: Color.GREEN)
            }
        })

        viewModel.positionSessionObserver.observe(viewLifecycleOwner, EventObserver {
            updateSessionPosition(it)
        })

        viewModel.positionEndingDigitObserver.observe(viewLifecycleOwner, EventObserver {
            endingDigitAdapter?.setPositionList(it)
        })

        viewModel.callMatchDetailsAsync()
        binding.pullRefreshLayout.setOnRefreshListener {
            viewModel.callMatchDetailsAsync()
        }

        viewModel.openBetScreenEvent.observe(viewLifecycleOwner, EventObserver {
            //            findNavController().navigate(R.id.nav_create_bet, bundle)
            val createBetFragment = CreateBetFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ConstantsBase.KEY_CREATE_BET_REQ, it)
                }
                listener = this@MatchDetailsFragment
            }
            val ft = childFragmentManager.beginTransaction()
            ft.addToBackStack(createBetFragment.tag)
            createBetFragment.show(ft, createBetFragment.tag)
        })

    }

    private val runnable = Runnable {
        viewModel.betStatus?.let {
            StringUtils.setEvents(it, binding.tvBetStatus)
        }
    }


    private fun initListeners() {

        binding.tvMyBets.setOnClickListener {
            childFragmentManager.run {
                if (findFragmentByTag(MyBetsFragment::class.java.simpleName) == null) {
                    beginTransaction().remove(viewModel.winnersFragment)
                        .commitAllowingStateLoss()
                    beginTransaction()
                        .add(
                            R.id.llMyBetContainer,
                            viewModel.myBetFragment,
                            MyBetsFragment::class.java.simpleName
                        )
                        .commitAllowingStateLoss()
                    binding.llMatchContainer.visibility = View.GONE
                }
            }
        }
        binding.tvContest.setOnClickListener {
            childFragmentManager.run {
                beginTransaction().remove(viewModel.myBetFragment).commitAllowingStateLoss()
                beginTransaction().remove(viewModel.winnersFragment).commitAllowingStateLoss()
            }
            binding.llMatchContainer.visibility = View.VISIBLE

        }
    }

    private fun setEvenOddData(market: Market) {
        binding.frameEvenOdd.visibility =
            if (market.runners?.isNotEmpty() == true) View.VISIBLE else View.GONE
        val marketStatus = market.status?.equals(ConstantsBase.open, true) == true
        binding.tvEvenOddType.text = market.betfairMarketType
        market.runners?.get(0)?.runner?.let {
            binding.tvOddEvenBack1.text = if (it.canBack && marketStatus) it.back else ""
            binding.tvOddEvenBack1.isClickable = (it.canBack && marketStatus)

            viewModel.evenMarket.let { item ->
                item?.id = it.id
                item?.B = it.back
                item?.canBack = it.canBack
                item?.marketId = market.id
            }
        }
        market.runners?.get(1)?.runner?.let {
            binding.tvOddEvenBack2.text = if (it.canBack && marketStatus) it.back else ""
            binding.tvOddEvenBack2.isClickable = (it.canBack && marketStatus)

            viewModel.oddMarket.let { item ->
                item?.id = it.id
                item?.B = it.back
                item?.canBack = it.canBack
                item?.marketId = market.id
            }
        }
    }


    private fun initSessionAdapterAdapter() {
        sessionAdapter = SessionAdapter(object : SessionAdapter.OnItemClickListener {
            override fun onYesClicked(session: Session) {
                viewModel.onSessionBetClicked(
                    session,
                    session.sessionRun?.yesRun,
                    true,
                    ConstantsBase.YES
                )
            }

            override fun onNoClicked(session: Session) {
                viewModel.onSessionBetClicked(
                    session,
                    session.sessionRun?.noRun,
                    false,
                    ConstantsBase.NO
                )
            }
        })
        binding.rvSession.adapter = sessionAdapter
    }

    private fun updateSessionPosition(it: String?) {
        binding.tvSessionPosition.text = it.toString()
        try {
            binding.tvSessionPosition.setTextColor(
                ColorUtils.getPositionColor(it?.toDouble() ?: 0.0)
            )
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private fun initEndingDigitAdapterAdapter() {
        endingDigitAdapter =
            EndingDigitAdapter(object : EndingDigitAdapter.OnItemClickListener {
                override fun onBackClicked(runner: Runner) {
                    viewModel.onEndingDigitClicked(runner)
                }
            })
        binding.rvEndingDigit.adapter = endingDigitAdapter
    }

    private fun updateSession(sessionsItem: SessionsItem) {
        try {
            if (sessionsItem.session?.status != ConstantsBase.close) {
                var isAdded = false
                sessionAdapter?.getSessionsList()?.forEachIndexed { index, item ->
                    if (sessionsItem.session?.id == item.session?.id) {
                        isAdded = true
                        sessionAdapter?.updateSession(sessionsItem, index)
                        return
                    }
                }
                if (!isAdded)
                    sessionAdapter?.addSession(sessionsItem)

            } else {
                sessionAdapter?.getSessionsList()?.forEach {
                    if (sessionsItem.session?.id == it.session?.id) {
                        sessionAdapter?.removeSessionItem(it)
                    }
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private fun showNetworkError() {
        val cm =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE)
        if (cm !is ConnectivityManager) return
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnected
        if (!isConnected) {
            snackBar = Snackbar.make(
                binding.root,
                resources.getString(R.string.no_connection),
                Snackbar.LENGTH_INDEFINITE
            )
            snackBar?.setAction("Retry") { v: View? ->
                snackBar?.dismiss()
                showNetworkError()
            }
            snackBar?.setActionTextColor(Color.GREEN)
            // Changing action button text color
            val textView: TextView? = snackBar?.view?.findViewById(R.id.snackbar_text)
            textView?.setTextColor(Color.WHITE)
            snackBar?.show()
        } else {
            if (snackBar != null) {
                if (viewModel.batTeamRunId == 0) viewModel.callMatchDetailsAsync()
                mqttConnection.connectToClient()
                snackBar?.dismiss()
            }
        }
    }

    private val networkStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            showNetworkError()
        }
    }

    override fun onBetSuccess(it: CreateBetRes) {
        viewModel.handleMarketPositionList(it.marketPosition)
    }

    override fun onSessionBetSuccess(createSessionBetRes: CreateSessionBetRes?) {
        updateSessionPosition(createSessionBetRes?.sessionPosition.toString())
    }

    override fun onResume() {
        super.onResume()
        mqttConnection.connectToClient()
    }
}