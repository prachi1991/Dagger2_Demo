package com.ballchalu.ui.match.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.R
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentMatchDetailsBinding
import com.ballchalu.mqtt.MqttMarket
import com.ballchalu.ui.match.details.adapter.EndingDigitAdapter
import com.ballchalu.ui.match.details.adapter.SessionAdapter
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.domain.match_details.*
import com.ccpp.shared.util.ConstantsBase
import com.ccpp.shared.util.viewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

class MatchDetailsFragment : BaseFragment() {

    private var snackBar: Snackbar? = null
    private var sessionAdapter: SessionAdapter? = null
    private var endingDigitAdapter: EndingDigitAdapter? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentMatchDetailsBinding
    private lateinit var viewModel: MatchDetailsViewModel

    private var batTeamRunId: Int? = 0
    private var bwlTeamRunId: Int? = 0
    private var drawTeamRunId: Int? = 0

    private var batTeamRunName = ""
    private var bwlTeamRunName = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentMatchDetailsBinding.inflate(inflater).apply {
            lifecycleOwner = this@MatchDetailsFragment
        }

        arguments?.let {
            viewModel.matchId = it.getInt(ConstantsBase.KEY_PROVIDER_ID)
        }
        registerReceiver()
        return binding.root
    }

    private fun registerReceiver() {
        requireContext().registerReceiver(
            networkStateReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        requireContext().registerReceiver(scoreUpdate, IntentFilter(ConstantsBase.ACTION_PUB_NUB))
        requireContext().registerReceiver(oddsReceiver, IntentFilter(ConstantsBase.ODDS_UPDATES))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireContext().unregisterReceiver(scoreUpdate)
        requireContext().unregisterReceiver(oddsReceiver)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSessionAdapterAdapter()
        initEndingDigitAdapterAdapter()

        viewModel.matchResult.observe(viewLifecycleOwner, EventObserver {
            setMatchData(it.match)
        })
        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            if (binding.pullRefreshLayout.isRefreshing)
                binding.pullRefreshLayout.isRefreshing = false
        })

        viewModel.sessionEvent.observe(viewLifecycleOwner, EventObserver {
            setSessionData(it)
        })

        viewModel.failure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
        })

        viewModel.winnerMarketEvent.observe(viewLifecycleOwner, EventObserver {
            setMarketData(it)
        })

        viewModel.evenOddMarketEvent.observe(viewLifecycleOwner, EventObserver {
            setEvenOddData(it)
        })

        viewModel.endingDigitMarketEvent.observe(viewLifecycleOwner, EventObserver {
            setEndingData(it)
        })

        viewModel.callMatchDetailsAsync()
        binding.pullRefreshLayout.setOnRefreshListener {
            viewModel.callMatchDetailsAsync()
        }
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


    private fun setMatchData(match: Match?) {
        binding.tvMatchTeam1.text = match?.team1
        binding.tvMatchTeam2.text = match?.team2

        binding.tvBetStatus.text = match?.heroicCommentary?.event ?: ""
        updateScore(match?.score)
    }

    private fun updateScore(score: Score?) {
        score?.batteamname?.let { binding.tvBatTeamName.text = it }
        score?.bwlteamname?.let { binding.tvBallTeamName.text = it }

        score?.batteamdesc?.let { binding.tvBatTeamScore.text = it }
        binding.tvBallTeamScore.text =
            if (score?.bwlteamdesc.isNullOrEmpty()) "Bowling..." else score?.bwlteamdesc

        batTeamRunName = score?.batteamname.toString()
        bwlTeamRunName = score?.bwlteamname.toString()
    }

    private fun setMarketData(market: Market?) {
        binding.tvMarketType.text = market?.heroicMarketType
        val items1: Runner? = market?.runners?.get(0)?.runner
        val items2: Runner? = market?.runners?.get(1)?.runner

        setBatTeamBhaav(items1?.back, items1?.lay, items1?.canBack, items1?.canLay)
        setBwlTeamBhaav(items2?.back, items2?.lay, items2?.canBack, items2?.canLay)

        if (items1 != null && items2 != null) {
            if (items1.betfairRunnerName?.trim().equals(batTeamRunName.trim())) {
                setBatTeamBhaav(items1.back, items1.lay, items1.canBack, items1.canLay)
                batTeamRunId = items1.id
            } else {
                setBwlTeamBhaav(items2.back, items2.lay, items2.canBack, items2.canLay)
                bwlTeamRunId = items2.id
            }

            if (items2.betfairRunnerName?.trim().equals(batTeamRunName.trim())) {
                setBwlTeamBhaav(items2.back, items2.lay, items2.canBack, items2.canLay)
                bwlTeamRunId = items2.id
            } else {
                setBatTeamBhaav(items1.back, items1.lay, items1.canBack, items1.canLay)
                batTeamRunId = items1.id
            }
            binding.tvBatTeamName.text = items1.betfairRunnerName
            binding.tvBallTeamName.text = items2.betfairRunnerName
        }
        setMarketStatus(market?.status?.equals(ConstantsBase.suspend, true) == true)
//            if (list.getMarket().getRunners().size() > 2) {
//                llDraw.setVisibility(View.VISIBLE)
//                val items3: Runner = list.getMarket().getRunners().get(2).getRunner()
//                setDrawTeamBhaav(
//                    items3.getBack(),
//                    items3.getLay(),
//                    items3.isCanBack(),
//                    items3.isCanLay()
//                )
//                tvDrawName.setText(items3.getBetfairRunnerName())
//                val drawTeamRunnerName: String = items3.getBetfairRunnerName()
//                drawTeamRunnerId = items3.id
//            } else llDraw.setVisibility(View.GONE)

//            tvMarketType.setText(
//                getString(
//                    R.string.market_type,
//                    list.getMarket().getBetfairMarketType()
//                )
//            )


    }

    private fun setSessionData(sessionList: List<SessionsItem>?) {
        binding.llSessionSection.visibility =
            if (sessionList?.isNotEmpty() == true) View.VISIBLE else View.GONE
        binding.rvSession.visibility =
            if (sessionList?.isNotEmpty() == true) View.VISIBLE else View.GONE
        sessionAdapter?.setItemList(sessionList)
    }

    private fun setEndingData(market: Market) {
        binding.llEndingDigitSection.visibility =
            if (market.runners?.isNotEmpty() == true) View.VISIBLE else View.GONE
        binding.tvEvenOddType.text = market.betfairMarketType
        endingDigitAdapter?.setItemList(market.runners, market.status)
    }


    private fun initSessionAdapterAdapter() {
        sessionAdapter = SessionAdapter()
        binding.rvSession.adapter = sessionAdapter
    }

    private fun initEndingDigitAdapterAdapter() {
        endingDigitAdapter = EndingDigitAdapter()
        binding.rvEndingDigit.adapter = endingDigitAdapter
    }

    private fun setBatTeamBhaav(back: String?, lay: String?, isBack: Boolean?, isLay: Boolean?) {
        binding.tvTeam1Lay.text = if (isBack == true) back else ""
        binding.tvTeam1Back.text = if (isLay == true) lay else ""
    }

    private fun setDrawTeamBhaav(back: String?, lay: String?, isBack: Boolean?, isLay: Boolean?) {
//        binding.tvDrawBack.text = if (isBack == true) back else ""
//        binding.tvDraw.text = if (isLay == true) lay else ""
    }

    private fun setBwlTeamBhaav(back: String?, lay: String?, isBack: Boolean?, isLay: Boolean?) {
        binding.tvTeam2Lay.text = if (isBack == true) back else ""
        binding.tvTeam2Back.text = if (isLay == true) lay else ""
    }

    private val scoreUpdate = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            intent.getStringExtra(ConstantsBase.PUB_NUB)?.let {
                try {
                    val oddJsonObject = JSONObject(it)
                    if (oddJsonObject.has(ConstantsBase.type)) {
                        val type = oddJsonObject.getString(ConstantsBase.type)
                        when {
                            type.equals(ConstantsBase.score, ignoreCase = true) -> {
                                if (viewModel.matchId == oddJsonObject.getInt(ConstantsBase.KEY_MATCH_ID)) {
                                    updateScore(oddJsonObject)
                                }
                            }
                            type.equals(ConstantsBase.SESSION, ignoreCase = true) -> {
//                                updateSession(oddJsonObject)
                            }
                            type.equals(ConstantsBase.ODD, ignoreCase = true) -> {
                                parseMarket(oddJsonObject)
                            }
                        }
                    }
                } catch (e: JSONException) {
                    Timber.e(e)
                }
            }
        }
    }

    private fun parseMarket(oddJsonObject: JSONObject) {
        if (!oddJsonObject.has(ConstantsBase.KEY_MARKET)) return
        val marketObject = oddJsonObject.getJSONObject(ConstantsBase.KEY_MARKET)
        if (viewModel.matchId == marketObject.getInt(ConstantsBase.KEY_MARKET)) {
            val market: MqttMarket =
                GsonBuilder().create().fromJson(oddJsonObject.toString(), MqttMarket::class.java)
            updateMarket(market, marketObject.getString(ConstantsBase.status))
        }
    }

    private fun updateMarket(market: MqttMarket, status: String) {

        when {
            status.trim().equals(ConstantsBase.open, true) -> {
                val run1 = market.market?.runners?.get(0)
                val run2 = market.market?.runners?.get(1)
                val run3 = market.market?.runners?.get(2)

                if (run1 == null || run2 == null || run1.id == 0 || run2.id == 0) return

                if (batTeamRunId == run1.id) setBatTeamBhaav(
                    run1.B,
                    run1.L,
                    run1.canBack,
                    run1.canLay
                )
                else setBwlTeamBhaav(run2.B, run2.L, run2.canBack, run2.canLay)

                if (bwlTeamRunId == run2.id) setBwlTeamBhaav(
                    run2.B,
                    run2.L,
                    run2.canBack,
                    run2.canLay
                )
                else setBatTeamBhaav(run1.B, run1.L, run1.canBack, run1.canLay)

                if (run3?.id != 0 && drawTeamRunId == run3?.id)
                    setDrawTeamBhaav(run3?.B, run3?.L, run3?.canBack, run3?.canLay)

                setMarketStatus(false)

            }
            status.equals(ConstantsBase.suspend, ignoreCase = true) -> {
                setMarketStatus(true)
            }
            status.equals(ConstantsBase.close, ignoreCase = true) -> {
                setMarketStatus(true)
            }
        }
    }

    private fun setMarketStatus(isSuspend: Boolean) {
        if (isSuspend) {
            setBatTeamBhaav("", "", isBack = true, isLay = true)
            setBwlTeamBhaav("", "", isBack = true, isLay = true)
            setDrawTeamBhaav("", "", isBack = true, isLay = true)
        }
    }

    private val oddsReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            intent.getStringExtra(ConstantsBase.ODD)?.let {
                try {
                    val oddJsonObject = JSONObject(it)
                    val type = oddJsonObject.getString(ConstantsBase.type)
                    if (type.equals("heroic_commentary", ignoreCase = true)) {
                        updateEvent(oddJsonObject)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun updateEvent(oddJsonObject: JSONObject) {
        try {
            if (viewModel.matchId == oddJsonObject.getInt(ConstantsBase.KEY_MATCH_ID)) {
                val jsonObject = oddJsonObject.getJSONObject(ConstantsBase.HEROIC_COMMENTARY)
                if (!TextUtils.isEmpty(jsonObject.getString(ConstantsBase.event)))
                    setEvents(jsonObject.getString(ConstantsBase.event).toLowerCase())
            }
        } catch (e: JSONException) {
            Timber.e(e)
        }
    }

    private fun updateScore(oddJsonObject: JSONObject) {
        val score: Score =
            GsonBuilder().create()
                .fromJson(oddJsonObject.getString("score").toString(), Score::class.java)
        updateScore(score)
    }

    private fun setEvents(event: String) {
        when (event) {
            ConstantsBase.WICKET -> {
                binding.tvBetStatus.text = event
            }
            ConstantsBase.WICKET1 -> {
                binding.tvBetStatus.text = " + 1"
            }
            ConstantsBase.WICKET2 -> {

                binding.tvBetStatus.text = " + 2"
            }
            ConstantsBase.WICKET3 -> {

                binding.tvBetStatus.text = " + 3"
            }
            ConstantsBase.WIDE_WICKET -> {
                binding.tvBetStatus.text = "Wide" + "\n" + "Wicket"
            }
            ConstantsBase.BALL_START -> {
                binding.tvBetStatus.text = event
            }
            ConstantsBase.THIRD_UMPIRE -> {

                binding.tvBetStatus.text = event
            }
            ConstantsBase.PLAYER_INJURED -> {
                binding.tvBetStatus.text = "Player" + "\n" + "Injured"

            }
            ConstantsBase.DOT -> {
                binding.tvBetStatus.text = "Dot"

            }
            ConstantsBase.RUN1 -> {
                binding.tvBetStatus.text = "1"

            }
            ConstantsBase.RUN2 -> {
                binding.tvBetStatus.text = "2"

            }
            ConstantsBase.RUN3 -> {
                binding.tvBetStatus.text = "3"
            }
            ConstantsBase.RUN5 -> {
                binding.tvBetStatus.text = "5"
            }
            ConstantsBase.FOUR -> {

                binding.tvBetStatus.text = event
            }
            ConstantsBase.SIX -> {

                binding.tvBetStatus.text = event
            }
            ConstantsBase.FREE_HIT -> {

                binding.tvBetStatus.text = event
            }
            ConstantsBase.NOT_OUT -> {

                binding.tvBetStatus.text = event
            }
            ConstantsBase.WIDE -> {

                binding.tvBetStatus.text = event
            }
            ConstantsBase.WIDE1 -> {

                binding.tvBetStatus.text = "+ 1"
            }
            ConstantsBase.WIDE2 -> {

                binding.tvBetStatus.text = "+ 2"
            }
            ConstantsBase.WIDE3 -> {

                binding.tvBetStatus.text = "+ 3"
            }
            ConstantsBase.WIDE4 -> {

                binding.tvBetStatus.text = "+ Wd"
            }
            ConstantsBase.NO_BALL -> {
                binding.tvBetStatus.text = event
            }
            ConstantsBase.NO_BALL1 -> {

                binding.tvBetStatus.text = "+ 1"
            }
            ConstantsBase.NO_BALL2 -> {

                binding.tvBetStatus.text = "+ 2"
            }
            ConstantsBase.NO_BALL3 -> {

                binding.tvBetStatus.text = "+ 3"
            }
            ConstantsBase.NO_BALL4 -> {

                binding.tvBetStatus.text = "+ Nb"
            }
            ConstantsBase.NO_BALL5 -> {
                binding.tvBetStatus.text = "+ 5"
            }
            ConstantsBase.NO_BALL6 -> {

                binding.tvBetStatus.text = "+ Nb"

            }
            else -> {
                binding.tvBetStatus.text = event

            }
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
            snackBar?.setAction("Retry", View.OnClickListener { v: View? ->
                snackBar?.dismiss()
                showNetworkError()
            })
            snackBar?.setActionTextColor(Color.GREEN)
            // Changing action button text color
            val textView: TextView? = snackBar?.view?.findViewById(R.id.snackbar_text)
            textView?.setTextColor(Color.WHITE)
            snackBar?.show()
        } else {
            if (snackBar != null) { //                ((MyApplication) getApplication()).connectToClient();
                if (batTeamRunId == 0) viewModel.callMatchDetailsAsync()
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