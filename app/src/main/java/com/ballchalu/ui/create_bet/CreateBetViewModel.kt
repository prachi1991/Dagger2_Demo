package com.ballchalu.ui.create_bet

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.R
import com.ballchalu.base.BaseViewModel
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.domain.MatchListingRes
import com.ccpp.shared.domain.create_bet.CreateBetReq
import com.ccpp.shared.domain.create_bet.CreateBetRes
import com.ccpp.shared.domain.create_bet.CreateSessionBetReq
import com.ccpp.shared.domain.create_bet.CreateSessionBetRes
import com.ccpp.shared.network.repository.CreateBetRepository
import com.ccpp.shared.network.repository.MatchesRepository
import com.ccpp.shared.util.ConstantsBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateBetViewModel @Inject constructor(
    private val matchesRepository: MatchesRepository,
    private val createBetRepository: CreateBetRepository,
    private val sharedPref: SharedPreferenceStorage,
    private val context: Context
) : BaseViewModel() {
    var createBetReq: CreateBetReq? = null
    var createSessionBetReq: CreateSessionBetReq? = null


    private val _matchListingObserver = MutableLiveData<Event<MatchListingRes>>()
    val inPlayListEvent: LiveData<Event<MatchListingRes>> = _matchListingObserver

    private val _upcomingMatchListEvent = MutableLiveData<Event<MatchListingRes>>()
    val upcomingMatchListEvent: LiveData<Event<MatchListingRes>> = _upcomingMatchListEvent

    fun callMatchListing(event_type: String, play_status: String) {
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = matchesRepository.getMatchesListing(event_type, play_status)) {
                is Results.Success -> handleSuccess(result.data, play_status)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    private fun handleSuccess(
        data: MatchListingRes, playStatus: String
    ) {
        if (playStatus == ConstantsBase.IN_PLAY) {
            _matchListingObserver.postValue(Event(data))
        } else if (playStatus == ConstantsBase.UPCOMING) {
            _upcomingMatchListEvent.postValue(Event(data))
        }
    }

    fun betArray(): ArrayList<Int> {
        return ArrayList<Int>().apply {
            add(0)
            add(100)
            add(500)
            add(1000)
            add(2000)
            add(5000)
            add(10000)
            add(25000)
            add(50000)
            add(75000)
            add(100000)
        }
    }


    private val _createBetObserver = MutableLiveData<Event<CreateBetRes>>()
    val createBetObserver: LiveData<Event<CreateBetRes>> = _createBetObserver

    fun callCreateBet(stack: String) {
        if (createBetReq == null) return
        createBetReq?.accessToken = sharedPref.token
        createBetReq?.stack = stack
        loading.postValue(Event(true))

        viewModelScope.launch(Dispatchers.IO) {
            when (val result = createBetRepository.callCreateBetAsync(createBetReq!!)) {
                is Results.Success -> handleCreateBetSuccess(result.data)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    private fun handleCreateBetSuccess(result: CreateBetRes) {
        _createBetObserver.postValue(Event(result))
    }

    private val _createSessionBetObserver = MutableLiveData<Event<CreateSessionBetRes>>()
    val createSessionBetObserver: LiveData<Event<CreateSessionBetRes>> = _createSessionBetObserver

    fun callCreateSessionBet(stack: String) {
        loading.postValue(Event(true))
        if (createSessionBetReq == null) return
        createSessionBetReq?.accessToken = sharedPref.token
        createSessionBetReq?.coinsDebited = stack

        viewModelScope.launch(Dispatchers.IO) {
            when (val result =
                createBetRepository.callCretateSessionBetAsync(createSessionBetReq!!)) {
                is Results.Success -> handleCreateSessionBetSuccess(result.data)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    private fun handleCreateSessionBetSuccess(result: CreateSessionBetRes) {
        _createSessionBetObserver.postValue(Event(result))
    }

    fun placeBet(betAmount: String?) {
        if (betAmount.isNullOrEmpty()) {
            Toast.makeText(
                context,
                context.getString(R.string.please_enter_amount),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            if (betAmount.toString().toInt() < 1) {
                Toast.makeText(
                    context,
                    context.getString(R.string.amount_should_be_greater),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                createBetReq?.let { callCreateBet(betAmount) }
                createSessionBetReq?.let { callCreateSessionBet(betAmount) }

            }
        }
    }


}