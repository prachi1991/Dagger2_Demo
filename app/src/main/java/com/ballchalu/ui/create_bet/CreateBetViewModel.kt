package com.ballchalu.ui.create_bet

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.base.BaseViewModel
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.domain.MatchListingRes
import com.ccpp.shared.domain.create_bet.CreateBetReq
import com.ccpp.shared.domain.create_bet.CreateBetRes
import com.ccpp.shared.network.Sessions
import com.ccpp.shared.network.repository.CreateBetRepository
import com.ccpp.shared.network.repository.MatchesRepository
import com.ccpp.shared.util.ConstantsBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateBetViewModel @Inject constructor(
    private val matchesRepository: MatchesRepository,
    private val createBetRepository: CreateBetRepository,
    private val sharedPref: SharedPreferenceStorage
): BaseViewModel()
{
    private val _matchListingObserver = MutableLiveData<Event<MatchListingRes>>()
    val inPlayListEvent: LiveData<Event<MatchListingRes>> = _matchListingObserver

    private val _upcomingmatchListingObserver = MutableLiveData<Event<MatchListingRes>>()
    val upcomingListEvent: LiveData<Event<MatchListingRes>> = _upcomingmatchListingObserver

    fun callMatchListing(event_type: String,play_status: String) {
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = matchesRepository.getMatchesListing(event_type,play_status)) {
                is Results.Success -> handleSuccess(result.data,play_status)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    private fun handleSuccess(data: MatchListingRes, playStatus: String
    ) {
        if(playStatus== ConstantsBase.IN_PLAY) {
            _matchListingObserver.postValue(Event(data))
        }
        else if(playStatus== ConstantsBase.UPCOMING) {
            _upcomingmatchListingObserver.postValue(Event(data))
        }
    }

    fun betArray(): ArrayList<Int> {
        val array = ArrayList<Int>()
        array.add(0)
        array.add(100)
        array.add(500)
        array.add(1000)
        array.add(2000)
        array.add(5000)
        array.add(10000)
        array.add(25000)
        array.add(50000)
        array.add(75000)
        array.add(100000)

        return array
    }


    private val _createBetObserver = MutableLiveData<Event<CreateBetRes>>()
    val createBetObserver: LiveData<Event<CreateBetRes>> = _createBetObserver

    fun callCreateBet(stack:String) {
        loading.postValue(Event(true))
        val createBetReq = CreateBetReq()
        createBetReq.accessToken = sharedPref.token
        createBetReq.matchId = "90"
        createBetReq.oddsType = "LAGAI"
        createBetReq.runnerId = "194"
        createBetReq.oddsVal = "1.59"
        createBetReq.marketId = "84"
        createBetReq.heroicMarketType = "match_winner"
        createBetReq.stack = stack
        createBetReq.contestsId = "58"

        viewModelScope.launch(Dispatchers.IO) {
            when (val result = createBetRepository.callCretateBetAsync(createBetReq)) {
                is Results.Success -> handleCreateBetSuccess(result.data)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    private fun handleCreateBetSuccess(result:CreateBetRes) {
        _createBetObserver.postValue(Event(result))
    }


}