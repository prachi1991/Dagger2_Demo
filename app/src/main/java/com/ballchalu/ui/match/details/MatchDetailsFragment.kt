package com.ballchalu.ui.match.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentMatchDetailsBinding
import com.ballchalu.mqtt.MqttMarket
import com.ballchalu.ui.match.details.adapter.EndingDigitAdapter
import com.ballchalu.ui.match.details.adapter.SessionAdapter
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.domain.match_details.*
import com.ccpp.shared.util.ConstantsBase
import com.ccpp.shared.util.viewModelProvider
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

class MatchDetailsFragment : BaseFragment() {

    private var sessionAdapter: SessionAdapter? = null
    private var endingDigitAdapter: EndingDigitAdapter? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentMatchDetailsBinding
    private lateinit var viewModel: MatchDetailsViewModel

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

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        requireContext().registerReceiver(scoreUpdate, IntentFilter("Action_Pubnub"))
        requireContext().registerReceiver(oddsReceiver, IntentFilter("Odds_Updates"))
    }

    override fun onStop() {
        super.onStop()
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
    }

    private fun setEvenOddData(market: Market) {
        binding.layoutEvenOdd.frameEvenOdd.visibility =
            if (market.runners?.isNotEmpty() == true) View.VISIBLE else View.GONE

        binding.layoutEvenOdd.tvEvenOddType.text = market.betfairMarketType
        binding.layoutEvenOdd.tvTeam1Back.text =
            if (market.runners?.get(0)?.runner?.canBack == true) market.runners?.get(0)?.runner?.back else ""
        binding.layoutEvenOdd.tvTeam2Back.text =
            if (market.runners?.get(1)?.runner?.canBack == true) market.runners?.get(1)?.runner?.back else ""
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

    }

    private fun setMarketData(market: Market?) {
        binding.tvMarketType.text = market?.heroicMarketType
        val items1: Runner? = market?.runners?.get(0)?.runner
        val items2: Runner? = market?.runners?.get(1)?.runner

        setBatTeamBhaav(items1?.back, items1?.lay, items1?.canBack, items1?.canLay)
        setBwlTeamBhaav(items2?.back, items2?.lay, items2?.canBack, items2?.canLay)
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
        endingDigitAdapter?.setItemList(market.runners)
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
                                //                            updateSession(oddJsonObject)
                            }
                            type.equals(ConstantsBase.ODD, ignoreCase = true) -> {
                                if (!oddJsonObject.has(ConstantsBase.KEY_MARKET)) return
                                val marketObject =
                                    oddJsonObject.getJSONObject(ConstantsBase.KEY_MARKET)
                                if (viewModel.matchId == marketObject.getInt(ConstantsBase.KEY_MARKET)) {
                                    val market: MqttMarket = GsonBuilder().create()
                                        .fromJson(oddJsonObject.toString(), MqttMarket::class.java)
                                    //                                updateMarket(market, marketObject.getString(ConstantsBase.status))
                                }
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
                val jsonObject = oddJsonObject.getJSONObject("heroic_commentary")
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
}