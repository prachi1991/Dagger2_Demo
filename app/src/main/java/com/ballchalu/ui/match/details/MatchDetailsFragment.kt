package com.ballchalu.ui.match.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ballchalu.R
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentMatchDetailsBinding
import com.ballchalu.mqtt.MqttConnection
import com.ballchalu.ui.match.details.adapter.EndingDigitAdapter
import com.ballchalu.ui.match.details.adapter.SessionAdapter
import com.ballchalu.utils.StringUtils
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.domain.match_details.Market
import com.ccpp.shared.domain.match_details.Runner
import com.ccpp.shared.domain.match_details.Session
import com.ccpp.shared.domain.match_details.SessionsItem
import com.ccpp.shared.util.ConstantsBase
import com.ccpp.shared.util.viewModelProvider
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import javax.inject.Inject

class MatchDetailsFragment : BaseFragment() {

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
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentMatchDetailsBinding.inflate(inflater).apply {
            lifecycleOwner = this@MatchDetailsFragment
            model = viewModel

            tvMyBets.setOnClickListener {
                val bundle = Bundle().apply {
                    putInt(ConstantsBase.KEY_CONTESTS_MATCH_ID, viewModel.contestsMatchId ?: 0)
                }
                findNavController().navigate(R.id.nav_my_bet, bundle)
            }

        }

        arguments?.let {
            viewModel.matchId = it.getInt(ConstantsBase.KEY_PROVIDER_ID)
            viewModel.contestsId = it.getInt(ConstantsBase.KEY_CONTESTS_ID)
            viewModel.contestsMatchId = it.getInt(ConstantsBase.KEY_CONTESTS_MATCH_ID)
        }
        registerReceiver()

        return binding.root
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
        initSessionAdapterAdapter()
        initEndingDigitAdapterAdapter()

        viewModel.matchResult.observe(viewLifecycleOwner, EventObserver { response ->
            binding.tvMatchTeam1.text = response?.match?.team1
            binding.tvMatchTeam2.text = response?.match?.team2
            binding.tvBetStatus.text = response?.match?.heroicCommentary?.event ?: ""
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
            binding.tvBatTeamName.text = market?.runners?.get(0)?.runner?.betfairRunnerName
            binding.tvBallTeamName.text = market?.runners?.get(1)?.runner?.betfairRunnerName
        })

        viewModel.evenOddMarketEvent.observe(viewLifecycleOwner, EventObserver {
            setEvenOddData(it)
        })

        viewModel.endingDigitMarketEvent.observe(viewLifecycleOwner, EventObserver { market ->
            binding.llEndingDigitSection.visibility =
                if (market.runners?.isNotEmpty() == true) View.VISIBLE else View.GONE
            binding.tvEvenOddType.text = market.betfairMarketType
            endingDigitAdapter?.setItemList(market.runners, market.status)
        })

        viewModel.betStatusEvent.observe(viewLifecycleOwner, EventObserver {
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
            viewModel.batTeamRunner = runners
        })

        viewModel.bwlTeamBhaavEvent.observe(viewLifecycleOwner, EventObserver { runners ->
            binding.tvTeam2Lay.text = if (runners?.canBack == true) runners.back else ""
            binding.tvTeam2Back.text = if (runners?.canLay == true) runners.lay else ""
            viewModel.bwlTeamRunner = runners
        })

        viewModel.updateEndingDigitDataEvent.observe(viewLifecycleOwner, EventObserver { market ->
            binding.llEndingDigitSection.visibility =
                if (market.runners?.isNotEmpty() == true) View.VISIBLE else View.GONE
            market.runners?.forEach {
                it.runner = Runner(back = it.B, canBack = it.canBack)
            }
            endingDigitAdapter?.updateEndingDigit(market.runners, market.status)
        })

        viewModel.updateEvenOddDataEvent.observe(viewLifecycleOwner, EventObserver { market ->
            binding.layoutEvenOdd.frameEvenOdd.visibility =
                if (market.runners?.isNotEmpty() == true) View.VISIBLE else View.GONE
            val marketStatus = market.status?.equals(ConstantsBase.open, true) == true
            binding.layoutEvenOdd.tvEvenOddType.text = market.betfairMarketType
            binding.layoutEvenOdd.tvTeam1Back.text =
                if (market.runners?.get(0)?.canBack == true && marketStatus) market.runners?.get(0)?.B else ""
            binding.layoutEvenOdd.tvTeam2Back.text =
                if (market.runners?.get(1)?.canBack == true && marketStatus) market.runners?.get(1)?.L else ""
        })

        viewModel.callMatchDetailsAsync()
        binding.pullRefreshLayout.setOnRefreshListener {
            viewModel.callMatchDetailsAsync()
        }

        viewModel.openBetScreenEvent.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(R.id.nav_create_bet)
        })

    }

    private fun setEvenOddData(market: Market) {
        binding.layoutEvenOdd.frameEvenOdd.visibility =
            if (market.runners?.isNotEmpty() == true) View.VISIBLE else View.GONE
        val marketStatus = market.status?.equals(ConstantsBase.open, true) == true
        binding.layoutEvenOdd.tvEvenOddType.text = market.betfairMarketType
        binding.layoutEvenOdd.tvTeam1Back.text =
            if (market.runners?.get(0)?.runner?.canBack == true && marketStatus) market.runners?.get(
                0
            )?.runner?.back else ""
        binding.layoutEvenOdd.tvTeam2Back.text =
            if (market.runners?.get(1)?.runner?.canBack == true && marketStatus) market.runners?.get(
                1
            )?.runner?.back else ""
    }


    private fun initSessionAdapterAdapter() {
        sessionAdapter = SessionAdapter(object : SessionAdapter.OnItemClickListener {
            override fun onYesClicked(session: Session) {
                Toast.makeText(
                    activity,
                    session.sessionRun?.yesRun.toString().plus("  " + session.sessionRun?.yesRate),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNoClicked(session: Session) {
                Toast.makeText(
                    activity,
                    session.sessionRun?.noRun.toString().plus("  " + session.sessionRun?.noRate),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        binding.rvSession.adapter = sessionAdapter
    }

    private fun initEndingDigitAdapterAdapter() {
        endingDigitAdapter = EndingDigitAdapter(object : EndingDigitAdapter.OnItemClickListener {
            override fun onBackClicked(runner: Runner) {
                Toast.makeText(
                    activity,
                    runner.back.plus("  " + runner.betfairRunnerName),
                    Toast.LENGTH_SHORT
                ).show()
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
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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
}