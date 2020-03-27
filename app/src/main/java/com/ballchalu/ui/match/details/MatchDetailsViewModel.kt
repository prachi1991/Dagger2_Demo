package com.ballchalu.ui.match.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.R
import com.ballchalu.base.BaseViewModel
import com.ballchalu.mqtt.MqttMarket
import com.ballchalu.ui.match.details.my_bets.MyBetsFragment
import com.ballchalu.ui.winners.WinnersFragment
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.domain.create_bet.BetDetailsBundle
import com.ccpp.shared.domain.create_bet.CreateBetReq
import com.ccpp.shared.domain.create_bet.CreateSessionBetReq
import com.ccpp.shared.domain.match_details.*
import com.ccpp.shared.domain.position.PositionMarketItem
import com.ccpp.shared.domain.position.Positions
import com.ccpp.shared.network.repository.MatchDetailsRepository
import com.ccpp.shared.util.ConstantsBase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class MatchDetailsViewModel @Inject constructor(
    private val sharePref: SharedPreferenceStorage,
    private val loginRepository: MatchDetailsRepository,
    private val context: Context
) :
    BaseViewModel() {
    var betStatus: String = ""
    var title: String? = null
    val myBetFragment: MyBetsFragment by lazy {
        MyBetsFragment().apply {
            arguments = Bundle().apply {
                putInt(ConstantsBase.KEY_CONTESTS_MATCH_ID, contestsMatchId)
            }
        }
    }
    val winnersFragment: WinnersFragment by lazy {
        WinnersFragment().apply {
            arguments = Bundle().apply {
                putInt(ConstantsBase.KEY_MATCH_ID, matchId)
                putInt(ConstantsBase.KEY_CONTESTS_MATCH_ID, contestsMatchId)
            }
        }
    }

    private var endingDigitMarket: Market? = null
    private var evenOddMarket: Market? = null
    private var sessionMarket: Market? = null
    var evenMarket: RunnersItem? = RunnersItem()
    var oddMarket: RunnersItem? = RunnersItem()
    var bwlTeamRunner: Runner? = null
    var batTeamRunner: Runner? = null
    var batTeamRunId: Int? = 0
    var bwlTeamRunId: Int? = 0

    var batTeamRunName = ""
    var bwlTeamRunName = ""

    var providerId: Int = 0
    var matchId: Int = 0
    var contestsId: Int = 0
    var contestsMatchId: Int = 0
    var isDeclared: Boolean = false

    //____________________________________variables__________________________//

    private val _matchResult = MutableLiveData<Event<MatchDetailsRes?>>()
    val matchResult: LiveData<Event<MatchDetailsRes?>> = _matchResult

    fun callMatchDetailsAsync() {
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = loginRepository.callMatchDetailsAsync(providerId)) {
                is Results.Success -> handleSuccess(result.data)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    //---------------------position section Start------------------------//


    private fun callPositionDetailsAsync(contestId: Int) {
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = loginRepository.callPositionDetailsAsync(contestId)) {
                is Results.Success -> handlePositionSuccess(result.data.positions)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    private val _positionMatchWinnerObserver = MutableLiveData<Event<MatchWinnerPosition?>>()
    val positionMatchWinnerObserver: LiveData<Event<MatchWinnerPosition?>> =
        _positionMatchWinnerObserver

    private val _positionEvenOddObserver = MutableLiveData<Event<EvenOddPosition?>>()
    val positionEvenOddObserver: LiveData<Event<EvenOddPosition?>> = _positionEvenOddObserver

    private val _positionEndingDigitObserver = MutableLiveData<Event<PositionMarketItem?>>()
    val positionEndingDigitObserver: LiveData<Event<PositionMarketItem?>> =
        _positionEndingDigitObserver


    private val _positionSessionObserver = MutableLiveData<Event<String?>>()
    val positionSessionObserver: LiveData<Event<String?>> = _positionSessionObserver


    private fun handlePositionSuccess(data: Positions?) {
        _positionSessionObserver.postValue(Event(data?.sessionPosition))
        handleMarketPositionList(data?.marketPosition)
    }

    fun handleMarketPositionList(marketPosition: List<PositionMarketItem>?) {
        marketPosition?.forEach { positionMarketItem ->
            when (positionMarketItem.heroicMarketType) {
                ConstantsBase.MATCH_WINNER -> {
                    positionMarketItem.runners?.forEach {
                        when (it?.runnerId) {
                            batTeamRunner?.id -> {
                                batTeamRunner?.runnerPosition = it?.runnerPosition
                            }
                            bwlTeamRunner?.id -> {
                                bwlTeamRunner?.runnerPosition = it?.runnerPosition
                            }
                        }
                    }
                    _positionMatchWinnerObserver.postValue(
                        Event(
                            MatchWinnerPosition(
                                batTeamRunner,
                                bwlTeamRunner
                            )
                        )
                    )
                }
                ConstantsBase.EVEN_ODD -> {
                    positionMarketItem.runners?.forEach {
                        when (it?.runnerId) {
                            evenMarket?.id -> {
                                evenMarket?.runnerPosition = it?.runnerPosition
                            }
                            oddMarket?.id -> {
                                oddMarket?.runnerPosition = it?.runnerPosition
                            }
                        }
                    }
                    _positionEvenOddObserver.postValue(
                        Event(
                            EvenOddPosition(
                                evenMarket,
                                oddMarket
                            )
                        )
                    )
                }
                ConstantsBase.ENDING_DIGIT -> {
                    _positionEndingDigitObserver.postValue(Event(positionMarketItem))
                }
            }
        }


    }
    //---------------------position section End------------------------//

    private fun handleSuccess(data: MatchDetailsRes?) {
        callPositionDetailsAsync(contestsId)
        data?.let {
            _matchResult.postValue(Event(data))
            batTeamRunName = it.match?.score?.batteamname ?: ""
            bwlTeamRunName = it.match?.score?.bwlteamname ?: ""
            _updateScoreEvent.postValue(Event(it.match?.score))
            setMarketData(data.markets)
            setSessionData(data.sessions)
        }
    }

    private val _sessionEvent = MutableLiveData<Event<List<SessionsItem>?>>()
    val sessionEvent: LiveData<Event<List<SessionsItem>?>> = _sessionEvent

    private fun setSessionData(sessions: List<SessionsItem>?) {
        sessions?.filter {
            it.session?.status == ConstantsBase.suspend || it.session?.status == ConstantsBase.open
        }?.let {
            _sessionEvent.postValue(Event(it))
        }
    }

    private val _winnerMarketEvent = MutableLiveData<Event<Market?>>()
    val winnerMarketEvent: LiveData<Event<Market?>> = _winnerMarketEvent

    private val _evenOddMarketEvent = MutableLiveData<Event<Market>>()
    val evenOddMarketEvent: LiveData<Event<Market>> = _evenOddMarketEvent

    private val _endingDigitMarketEvent = MutableLiveData<Event<Market>>()
    val endingDigitMarketEvent: LiveData<Event<Market>> = _endingDigitMarketEvent


    private fun setMarketData(marketList: List<MarketsItem>?) {
        marketList?.forEachIndexed { _, marketsItem ->
            val market: Market? = marketsItem.market
            when {
                (market?.heroicMarketType?.equals(ConstantsBase.MATCH_WINNER, true) == true
                        && (market.status?.equals(ConstantsBase.open, true) == true
                        || market.status?.equals(ConstantsBase.suspend, true) == true))
                -> {
                    setMarketData(market)
                }
                market?.heroicMarketType?.equals(ConstantsBase.EVEN_ODD, true) == true -> {
                    market.let {
                        evenOddMarket = market
                        _evenOddMarketEvent.postValue(Event(it))
                    }
                }
                market?.heroicMarketType?.equals(ConstantsBase.ENDING_DIGIT, true) == true -> {
                    market.let {
                        endingDigitMarket = market
                        _endingDigitMarketEvent.postValue(Event(it))
                    }
                }
            }

        }
    }

    private fun setMarketData(market: Market?) {
        _winnerMarketEvent.postValue(Event(market))
        val run1: Runner? = market?.runners?.get(0)?.runner.apply {
            this?.marketId = market?.id
            this?.status = market?.status
        }
        val run2: Runner? = market?.runners?.get(1)?.runner.apply {
            this?.marketId = market?.id
            this?.status = market?.status
        }
        if (market?.status?.equals(ConstantsBase.open, true) == true) {
            if (run1 != null && run2 != null) {
                if (run1.betfairRunnerName?.trim().equals(batTeamRunName.trim())) {
                    _batTeamBhaavEvent.postValue(Event(run1))
                    batTeamRunId = run1.id
                    batTeamRunner = run1
                } else {
                    _bwlTeamBhaavEvent.postValue(Event(run2))
                    bwlTeamRunId = run2.id
                    bwlTeamRunner = run2
                }

                if (run2.betfairRunnerName?.trim().equals(bwlTeamRunName.trim())) {
                    _bwlTeamBhaavEvent.postValue(Event(run2))
                    bwlTeamRunId = run2.id
                    bwlTeamRunner = run2
                } else {
                    _batTeamBhaavEvent.postValue(Event(run1))
                    batTeamRunner = run1
                    batTeamRunId = run1.id
                }
                setBatStatus(run1.canBack || run1.canLay || run2.canBack || run2.canLay)
            }
        }
//        _marketStatusEvent.postValue(Event)
        if (market?.status?.equals(ConstantsBase.suspend, true) == true) {
            setBatStatus(false)
            _batTeamBhaavEvent.postValue(null)
            _batTeamBhaavEvent.postValue(null)
        }
    }


    private val _betStatusEvent = MutableLiveData<Event<String>>()
    val betStatusEvent: LiveData<Event<String>> = _betStatusEvent

    val oddsReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            intent.getStringExtra(ConstantsBase.ODDS)?.let {
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

    private fun updateEvent(oddJsonObject: JSONObject) {
        if (providerId == oddJsonObject.getInt(ConstantsBase.KEY_MATCH_ID)) {
            oddJsonObject.getJSONObject(ConstantsBase.HEROIC_COMMENTARY).let {
                if (it.getString(ConstantsBase.event).isNotEmpty()) {
                    betStatus = if (checkStatus()) ConstantsBase.betOpen else ConstantsBase.betClose
                    _betStatusEvent.postValue(
                        Event(
                            it.getString(ConstantsBase.event).toLowerCase(
                                Locale.ENGLISH
                            )
                        )
                    )
                }
            }
        }
    }

    private fun checkStatus(): Boolean {
        batTeamRunner?.let {
            if (it.status?.equals(ConstantsBase.open, true) == true)
                if (it.canLay || it.canBack)
                    return true
        }
        bwlTeamRunner?.let {
            if (it.status?.equals(ConstantsBase.open, true) == true)
                if (it.canLay || it.canBack)
                    return true
        }

        evenOddMarket?.let { market ->
            if (market.status?.equals(ConstantsBase.open, true) == true)
                market.runners?.forEach { it ->
                    if (it.canLay || it.canBack) {
                        return true
                    }
                }
        }
        endingDigitMarket?.let {
            if (it.status?.equals(ConstantsBase.open, true) == true)
                it.runners?.forEach { run ->
                    if (run.canBack) return true
                }
        }
        return false
    }

    val scoreUpdate = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            intent.getStringExtra(ConstantsBase.PUB_NUB)?.let {
                Timber.e(it)
                try {
                    val oddJsonObject = JSONObject(it)
                    if (oddJsonObject.has(ConstantsBase.type)) {
                        val type = oddJsonObject.getString(ConstantsBase.type)
                        when {
                            type.equals(ConstantsBase.score, ignoreCase = true) -> {
                                if (providerId == oddJsonObject.getInt(ConstantsBase.KEY_MATCH_ID)) {
                                    parseScore(oddJsonObject)
                                }
                            }
                            type.equals(ConstantsBase.SESSION, ignoreCase = true) -> {
                                updateSession(oddJsonObject)
                            }
                            type.equals(ConstantsBase.ODDS, ignoreCase = true) -> {
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

    private val _updateScoreEvent = MutableLiveData<Event<Score?>>()
    val updateScoreEvent: LiveData<Event<Score?>> = _updateScoreEvent

    private fun parseScore(oddJsonObject: JSONObject) {
        val score: Score =
            GsonBuilder().create()
                .fromJson(
                    oddJsonObject.getString(ConstantsBase.score).toString(),
                    Score::class.java
                )
        batTeamRunName = score.batteamname ?: ""
        bwlTeamRunName = score.bwlteamname ?: ""
        _updateScoreEvent.postValue(Event(score))
    }

    private val _updateSessionEvent = MutableLiveData<Event<SessionsItem>>()
    val updateSessionEvent: LiveData<Event<SessionsItem>> = _updateSessionEvent

    private fun updateSession(sessionObject: JSONObject) {
        val jsonObject: JSONObject = sessionObject.getJSONObject(ConstantsBase.SESSION)
        if (providerId == jsonObject.getInt(ConstantsBase.KEY_MATCH_ID)) {
            val sessionsItem: SessionsItem =
                Gson().fromJson(sessionObject.toString(), SessionsItem::class.java)
            _updateSessionEvent.postValue(Event(sessionsItem))
        }
    }

    private val _updateEvenOddDataEvent = MutableLiveData<Event<Market>>()
    val updateEvenOddDataEvent: LiveData<Event<Market>> = _updateEvenOddDataEvent

    private val _updateEndingDigitDataEvent = MutableLiveData<Event<Market>>()
    val updateEndingDigitDataEvent: LiveData<Event<Market>> = _updateEndingDigitDataEvent

    private fun parseMarket(oddJsonObject: JSONObject) {
        if (!oddJsonObject.has(ConstantsBase.KEY_MARKET)) return
        val marketObject = oddJsonObject.getJSONObject(ConstantsBase.KEY_MARKET)
        if (providerId == marketObject.getInt(ConstantsBase.KEY_MATCH_ID)) {
            val market: Market? =
                GsonBuilder().create().fromJson(oddJsonObject.toString(), MqttMarket::class.java)
                    ?.market
            when (market?.heroicMarketType?.toLowerCase(Locale.ENGLISH)) {
                ConstantsBase.EVEN_ODD -> {
                    _updateEvenOddDataEvent.value = Event(market)
                    evenOddMarket = market
                }
                ConstantsBase.ENDING_DIGIT -> {
                    endingDigitMarket = market
                    _updateEndingDigitDataEvent.value = Event(market)
                }
                ConstantsBase.MATCH_WINNER -> {
                    updateMarket(market)
                }
            }
        }
    }

    private val _batTeamBhaavEvent = MutableLiveData<Event<Runner?>>()
    val batTeamBhaavEvent: LiveData<Event<Runner?>> = _batTeamBhaavEvent

    private val _bwlTeamBhaavEvent = MutableLiveData<Event<Runner?>>()
    val bwlTeamBhaavEvent: LiveData<Event<Runner?>> = _bwlTeamBhaavEvent

    private fun updateMarket(market: Market?) {
        when (market?.status?.trim()) {
            ConstantsBase.open -> {
                val run1: RunnersItem? = market.runners?.get(0).apply {
                    this?.marketId = market.id
                    this?.status = market.status
                }
                val run2: RunnersItem? = market.runners?.get(1).apply {
                    this?.marketId = market.id
                    this?.status = market.status
                }
                val run3: RunnersItem? = when {
                    market.runners?.size ?: 0 > 2 -> market.runners?.get(2)
                    else -> null
                }
                if (run1 == null || run2 == null || run1.id == 0 || run2.id == 0) return

                if (batTeamRunId == run1.id) {
                    run1.betfairRunnerName = batTeamRunner?.betfairRunnerName
                    _batTeamBhaavEvent.value = Event(parseRunnerObject(run1))
                } else {
                    run2.betfairRunnerName = bwlTeamRunner?.betfairRunnerName
                    _bwlTeamBhaavEvent.value = Event(parseRunnerObject(run2))
                }


                if (bwlTeamRunId == run2.id) {
                    run2.betfairRunnerName = bwlTeamRunner?.betfairRunnerName
                    _bwlTeamBhaavEvent.value = Event(parseRunnerObject(run2))
                } else {
                    run1.betfairRunnerName = batTeamRunner?.betfairRunnerName
                    _batTeamBhaavEvent.value = Event(parseRunnerObject(run1))
                }
                setBatStatus(run1.canBack || run1.canLay || run2.canBack || run2.canLay)

//                if (run3?.id != 0 && drawTeamRunId == run3?.id)
//                    setDrawTeamBhaav(run3?.B, run3?.L, run3?.canBack, run3?.canLay)
            }
            ConstantsBase.suspend, ConstantsBase.close -> {
                setBatStatus(false)
                _batTeamBhaavEvent.postValue(Event(Runner()))
                _batTeamBhaavEvent.postValue(Event(Runner()))
            }
        }
    }

    private fun parseRunnerObject(runnersItem: RunnersItem): Runner {
        return Runner(
            id = runnersItem.id,
            status = runnersItem.status,
            back = runnersItem.B,
            canBack = runnersItem.canBack,
            lay = runnersItem.L,
            canLay = runnersItem.canLay,
            betfairRunnerName = runnersItem.betfairRunnerName,
            betfairSelectionId = runnersItem.betfairSelectionId,
            marketId = runnersItem.marketId
        )
    }

    private fun setBatStatus(isOpen: Boolean) {
        _betStatusEvent.postValue(Event(if (isOpen) ConstantsBase.betOpen else ConstantsBase.betClose))

    }

    private val _openBetScreenEvent = MutableLiveData<Event<BetDetailsBundle?>>()
    val openBetScreenEvent: LiveData<Event<BetDetailsBundle?>> = _openBetScreenEvent

    fun onMatchWinnerClicked(isLay: Boolean, isTeam1: Boolean) {
        val runner = if (isTeam1) batTeamRunner else bwlTeamRunner
        val oddValue = if (isLay) runner?.lay else runner?.back
        if (oddValue.isNullOrEmpty()) return
        _openBetScreenEvent.value = Event(
            BetDetailsBundle(
                betReq =
                CreateBetReq(
                    matchId = providerId.toString(),
                    oddsType = if (isLay) ConstantsBase.KHAI else ConstantsBase.LAGAI,
                    runnerId = runner?.id,
                    oddsVal = oddValue,
                    marketId = runner?.marketId,
                    heroicMarketType = ConstantsBase.MATCH_WINNER,
                    contestsId = contestsId,
                    evenTypeTitle = runner?.betfairRunnerName
                )
            )
        )
    }

    fun onSessionBetClicked(
        session: Session,
        run: String?,
        isLagai: Boolean,
        yesNoType: String
    ) {
        val oddVal = if (isLagai) session.sessionRun?.yesRate else session.sessionRun?.noRate
        if (oddVal.isNullOrEmpty()) return
        _openBetScreenEvent.value = Event(
            BetDetailsBundle(
                betSessionReq =
                CreateSessionBetReq(
                    matchId = providerId.toString(),
                    runs = if (isLagai) session.sessionRun?.yesRun else session.sessionRun?.noRun,
                    sessionBetType = if (isLagai) ConstantsBase.YES else ConstantsBase.NO,
                    sessionId = session.id,
                    heroicMarketType = session.title,
                    sessionRunId = session.sessionRun?.id,
                    oddValue = if (isLagai) session.sessionRun?.yesRate else session.sessionRun?.noRate,
                    contestsId = contestsId,
                    evenTypeTitle = context.resources.getString(
                        R.string.rate_odd_even_type,
                        run ?: "",
                        yesNoType
                    )
                )
            )
        )
    }

    fun onEndingDigitClicked(runner: Runner?) {
        if (runner?.canBack == false) return
        _openBetScreenEvent.value = Event(
            BetDetailsBundle(
                betReq =
                CreateBetReq(
                    matchId = providerId.toString(),
                    oddsType = ConstantsBase.LAGAI,
                    runnerId = runner?.id,
                    oddsVal = runner?.back,
                    marketId = runner?.marketId,
                    heroicMarketType = ConstantsBase.ENDING_DIGIT,
                    contestsId = contestsId,
                    evenTypeTitle = runner?.betfairRunnerName
                )
            )
        )
    }

    fun onEvenOddClicked(isEvenType: Boolean) {
        val market = if (isEvenType) evenMarket else oddMarket
        if (market?.canBack == false) return
        _openBetScreenEvent.value = Event(
            BetDetailsBundle(
                betReq =
                CreateBetReq(
                    matchId = providerId.toString(),
                    oddsType = ConstantsBase.LAGAI,
                    runnerId = market?.id,
                    oddsVal = market?.B,
                    marketId = market?.marketId,
                    heroicMarketType = ConstantsBase.EVEN_ODD,
                    contestsId = contestsId,
                    evenTypeTitle = if (isEvenType) ConstantsBase.EVEN else ConstantsBase.ODD
                )
            )
        )
    }

}
