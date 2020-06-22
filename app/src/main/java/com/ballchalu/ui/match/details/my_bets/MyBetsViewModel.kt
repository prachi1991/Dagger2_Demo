package com.ballchalu.ui.match.details.my_bets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.base.BaseViewModel
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.domain.my_bets.MyBetsRes
import com.ccpp.shared.domain.my_bets.UserMyBet
import com.ccpp.shared.network.repository.MyBetsRepository
import com.ccpp.shared.util.ConstantsBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MyBetsViewModel @Inject constructor(
    private val myBetsRepository: MyBetsRepository
) : BaseViewModel() {
    var providerId: Int = 0

    private val _betMatchWinnerObserver = MutableLiveData<Event<ArrayList<UserMyBet>?>>()
    val betMatchWinnerObserver: LiveData<Event<ArrayList<UserMyBet>?>> = _betMatchWinnerObserver

    private val _betSessionObserver = MutableLiveData<Event<ArrayList<UserMyBet>?>>()
    val betSessionObserver: LiveData<Event<ArrayList<UserMyBet>?>> = _betSessionObserver

    private val _betEvenOddObserver = MutableLiveData<Event<ArrayList<UserMyBet>?>>()
    val betEvenOddObserver: LiveData<Event<ArrayList<UserMyBet>?>> = _betEvenOddObserver

    private val _betEndingDigitObserver = MutableLiveData<Event<ArrayList<UserMyBet>?>>()
    val betEndingDigitObserver: LiveData<Event<ArrayList<UserMyBet>?>> = _betEndingDigitObserver

    private val _isEmpty = MutableLiveData<Event<Boolean>>()
    val isEmpty: LiveData<Event<Boolean>> = _isEmpty

    fun callMyBetsDetailsAsync() {
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = myBetsRepository.callMyBetAsync(providerId.toString())) {
                is Results.Success -> handleSuccess(result.data)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    private fun handleSuccess(data: MyBetsRes?) {
        val betMatchWinnerArrayList = arrayListOf<UserMyBet>()
        val betSessionArrayList = arrayListOf<UserMyBet>()
        val betEvenOddArrayList = arrayListOf<UserMyBet>()
        val betEndingDigitArrayList = arrayListOf<UserMyBet>()
        data?.userBets?.forEach {
            when (it.userBet?.heroicMarketType?.trim()?.toLowerCase(Locale.US)) {
                ConstantsBase.MATCH_WINNER -> betMatchWinnerArrayList.add(it.userBet!!)
                ConstantsBase.EVEN_ODD -> betEvenOddArrayList.add(it.userBet!!)
                ConstantsBase.ENDING_DIGIT -> betEndingDigitArrayList.add(it.userBet!!)
                null -> if (it.userBet?.sessionId != null) betSessionArrayList.add(it.userBet!!)

            }
        }
        val isNull =
            (betEndingDigitArrayList.isEmpty() && betSessionArrayList.isEmpty() && betEvenOddArrayList.isEmpty() && betMatchWinnerArrayList.isEmpty())

        _isEmpty.postValue(Event(isNull))

        _betMatchWinnerObserver.postValue(Event(betMatchWinnerArrayList))
        _betSessionObserver.postValue(Event(betSessionArrayList))
        _betEvenOddObserver.postValue(Event(betEvenOddArrayList))
        _betEndingDigitObserver.postValue(Event(betEndingDigitArrayList))
    }

}