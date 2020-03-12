package com.ballchalu.ui.match.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.base.BaseViewModel
import com.ballchalu.mqtt.MqttMarket
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.domain.match_details.*
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
    private val sharedPreferenceStorage: SharedPreferenceStorage,
    private val loginRepository: MatchDetailsRepository,
    private val context: Context
) :
    BaseViewModel() {
    var bwlTeamRunner: Runner? = null
    var batTeamRunner: Runner? = null
    var batTeamRunId: Int? = 0
    var bwlTeamRunId: Int? = 0
    var drawTeamRunId: Int? = 0

    var batTeamRunName = ""
    var bwlTeamRunName = ""

    var matchId: Int = 0
    //____________________________________variables__________________________//

    private val _matchResult = MutableLiveData<Event<MatchDetailsRes?>>()
    val matchResult: LiveData<Event<MatchDetailsRes?>> = _matchResult

    fun callMatchDetailsAsync() {
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = loginRepository.callMatchDetailsAsync(matchId)) {
                is Results.Success -> handleSuccess(result.data)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    private fun handleSuccess(data: MatchDetailsRes?) {
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
        sessions?.filter { it.session?.status == ConstantsBase.suspend || it.session?.status == ConstantsBase.open }
            ?.let {
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
                    market.let {
                        setMarketData(market)
                    }
                }
                market?.heroicMarketType?.equals(ConstantsBase.EVEN_ODD, true) == true -> {
                    market.let {
                        _evenOddMarketEvent.postValue(Event(it))
                    }
                }
                market?.heroicMarketType?.equals(ConstantsBase.ENDING_DIGIT, true) == true -> {
                    market.let {
                        _endingDigitMarketEvent.postValue(Event(it))
                    }
                }
            }

        }
    }

    private fun setMarketData(market: Market?) {
        _winnerMarketEvent.postValue(Event(market))
        val items1: Runner? = market?.runners?.get(0)?.runner
        val items2: Runner? = market?.runners?.get(1)?.runner

        if (market?.status?.equals(ConstantsBase.open, true) == true) {
            if (items1 != null && items2 != null) {
                if (items1.betfairRunnerName?.trim().equals(batTeamRunName.trim())) {
                    _batTeamBhaavEvent.postValue(Event(items1))
                    batTeamRunId = items1.id
                } else {
                    _bwlTeamBhaavEvent.postValue(Event(items2))
                    bwlTeamRunId = items2.id
                }

                if (items2.betfairRunnerName?.trim().equals(bwlTeamRunName.trim())) {
                    _bwlTeamBhaavEvent.postValue(Event(items2))
                    bwlTeamRunId = items2.id
                } else {
                    _batTeamBhaavEvent.postValue(Event(items1))
                    batTeamRunId = items1.id
                }
            }
        }
//        _marketStatusEvent.postValue(Event)
        if (market?.status?.equals(ConstantsBase.suspend, true) == true) {
            _batTeamBhaavEvent.postValue(null)
            _batTeamBhaavEvent.postValue(null)
        }
    }


    private val _betStatusEvent = MutableLiveData<Event<String>>()
    val betStatusEvent: LiveData<Event<String>> = _betStatusEvent

    val oddsReceiver = object : BroadcastReceiver() {
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

    private fun updateEvent(oddJsonObject: JSONObject) {
        if (matchId == oddJsonObject.getInt(ConstantsBase.KEY_MATCH_ID)) {
            oddJsonObject.getJSONObject(ConstantsBase.HEROIC_COMMENTARY).let {
                if (it.getString(ConstantsBase.event).isNotEmpty())
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
                                if (matchId == oddJsonObject.getInt(ConstantsBase.KEY_MATCH_ID)) {
                                    parseScore(oddJsonObject)
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
        if (matchId == jsonObject.getInt(ConstantsBase.KEY_MATCH_ID)) {
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
        if (matchId == marketObject.getInt(ConstantsBase.KEY_MATCH_ID)) {
            val market: Market? =
                GsonBuilder().create().fromJson(oddJsonObject.toString(), MqttMarket::class.java)
                    ?.market
            when (market?.heroicMarketType?.toLowerCase(Locale.ENGLISH)) {
                ConstantsBase.EVEN_ODD -> _updateEvenOddDataEvent.value = Event(market)
                ConstantsBase.ENDING_DIGIT -> _updateEndingDigitDataEvent.value = Event(market)
                ConstantsBase.MATCH_WINNER -> updateMarket(market)
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
                val run1: RunnersItem? = market.runners?.get(0)
                val run2: RunnersItem? = market.runners?.get(1)
                val run3: RunnersItem? = when {
                    market.runners?.size ?: 0 > 2 -> market.runners?.get(2)
                    else -> null
                }
                if (run1 == null || run2 == null || run1.id == 0 || run2.id == 0) return

                if (batTeamRunId == run1.id) _batTeamBhaavEvent.value =
                    Event(parseRunnerObject(run1))
                else _bwlTeamBhaavEvent.value = Event(parseRunnerObject(run2))

                if (bwlTeamRunId == run2.id) _bwlTeamBhaavEvent.value =
                    Event(parseRunnerObject(run2))
                else _batTeamBhaavEvent.value = Event(parseRunnerObject(run1))

//                if (run3?.id != 0 && drawTeamRunId == run3?.id)
//                    setDrawTeamBhaav(run3?.B, run3?.L, run3?.canBack, run3?.canLay)
            }
            ConstantsBase.suspend, ConstantsBase.close -> {
                _batTeamBhaavEvent.postValue(Event(Runner()))
                _batTeamBhaavEvent.postValue(Event(Runner()))
            }
        }
    }

    private fun parseRunnerObject(runnersItem: RunnersItem): Runner {
        return Runner(
            back = runnersItem.B,
            canBack = runnersItem.canBack,
            lay = runnersItem.L,
            canLay = runnersItem.canLay
        )
    }

    private val _openBetScreenEvent = MutableLiveData<Event<Runner?>>()
    val openBetScreenEvent: LiveData<Event<Runner?>> = _openBetScreenEvent

    fun onTeam1BackClicked() {
        _openBetScreenEvent.value = Event(batTeamRunner)
    }

    fun onTeam1LayClicked() {
        _openBetScreenEvent.value = Event(batTeamRunner)
    }

    fun onTeam2BackClicked() {
        _openBetScreenEvent.value = Event(bwlTeamRunner)
    }

    fun onTeam2LayClicked() {
        _openBetScreenEvent.value = Event(bwlTeamRunner)
    }
}
