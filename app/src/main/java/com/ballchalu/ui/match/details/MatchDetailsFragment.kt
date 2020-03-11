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
import com.ballchalu.mqtt.MqttConnection
import com.ballchalu.mqtt.MqttMarket
import com.ballchalu.ui.match.details.adapter.EndingDigitAdapter
import com.ballchalu.ui.match.details.adapter.SessionAdapter
import com.ballchalu.utils.StringUtils
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.domain.match_details.*
import com.ccpp.shared.util.ConstantsBase
import com.ccpp.shared.util.viewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.util.*
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
        binding.tvMarketType.text = market?.betfairMarketType
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

            if (items2.betfairRunnerName?.trim().equals(bwlTeamRunName.trim())) {
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
                Timber.e(it)
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
                                updateSession(oddJsonObject)
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
    private val oddsReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            intent.getStringExtra(ConstantsBase.ODD)?.let {
                try {
                    val oddJsonObject = JSONObject(it)
                    val type = oddJsonObject.getString(ConstantsBase.type)
                    if (type.equals(ConstantsBase.HEROIC_COMMENTARY, ignoreCase = true)) {
                        updateEvent(oddJsonObject)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun parseMarket(oddJsonObject: JSONObject) {
        if (!oddJsonObject.has(ConstantsBase.KEY_MARKET)) return
        val marketObject = oddJsonObject.getJSONObject(ConstantsBase.KEY_MARKET)
        if (viewModel.matchId == marketObject.getInt(ConstantsBase.KEY_MATCH_ID)) {
            val market: Market? =
                GsonBuilder().create().fromJson(oddJsonObject.toString(), MqttMarket::class.java)
                    ?.market
            when {
                market?.heroicMarketType?.equals(
                    ConstantsBase.EVEN_ODD,
                    true
                ) == true -> updateEvenOddData(market)
                market?.heroicMarketType?.equals(
                    ConstantsBase.ENDING_DIGIT,
                    true
                ) == true -> updateEndingDigitData(market)
                else -> updateMarket(market, marketObject.getString(ConstantsBase.status))
            }
        }
    }

    private fun updateSession(sessionObject: JSONObject) {
        try {
            val jsonObject: JSONObject = sessionObject.getJSONObject(ConstantsBase.SESSION)
            if (viewModel.matchId == jsonObject.getInt(ConstantsBase.KEY_MATCH_ID)) {
                val sessionsItem: SessionsItem =
                    Gson().fromJson(sessionObject.toString(), SessionsItem::class.java)

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
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateEndingDigitData(market: Market) {
        binding.llEndingDigitSection.visibility =
            if (market.runners?.isNotEmpty() == true) View.VISIBLE else View.GONE
        market.runners?.forEach {
            it.runner = Runner(back = it.B, canBack = it.canBack)
        }
        endingDigitAdapter?.updateEndingDigit(market.runners, market.status)
    }

    private fun updateEvenOddData(market: Market) {
        binding.layoutEvenOdd.frameEvenOdd.visibility =
            if (market.runners?.isNotEmpty() == true) View.VISIBLE else View.GONE
        val marketStatus = market.status?.equals(ConstantsBase.open, true) == true
        binding.layoutEvenOdd.tvEvenOddType.text = market.betfairMarketType
        binding.layoutEvenOdd.tvTeam1Back.text =
            if (market.runners?.get(0)?.canBack == true && marketStatus) market.runners?.get(0)?.B else ""
        binding.layoutEvenOdd.tvTeam2Back.text =
            if (market.runners?.get(1)?.canBack == true && marketStatus) market.runners?.get(1)?.L else ""
    }

    private fun updateMarket(market: Market?, status: String) {

        when {
            status.trim().equals(ConstantsBase.open, true) -> {
                val run1 = market?.runners?.get(0)
                val run2 = market?.runners?.get(1)
                val run3 =
                    if (market?.runners?.size ?: 0 > 2) market?.runners?.get(2) else null

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
        } else {

        }
    }


    private fun updateEvent(oddJsonObject: JSONObject) {
        try {
            if (viewModel.matchId == oddJsonObject.getInt(ConstantsBase.KEY_MATCH_ID)) {
                val jsonObject = oddJsonObject.getJSONObject(ConstantsBase.HEROIC_COMMENTARY)
                if (!TextUtils.isEmpty(jsonObject.getString(ConstantsBase.event)))
                    StringUtils.setEvents(
                        jsonObject.getString(ConstantsBase.event).toLowerCase(
                            Locale.ENGLISH
                        ), binding.tvBetStatus
                    )
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
                if (batTeamRunId == 0) viewModel.callMatchDetailsAsync()
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