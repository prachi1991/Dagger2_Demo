package com.ballchalu.ui.match.details.my_bets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.base.BaseViewModel
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.domain.contest.CreateContestRes
import com.ccpp.shared.domain.match_details.MatchDetailsRes
import com.ccpp.shared.domain.my_bets.MyBetsRes
import com.ccpp.shared.network.repository.MyBetsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyBetsViewModel @Inject constructor(
    private val myBetsRepository: MyBetsRepository
):BaseViewModel()
{
    var matchId: Int = 0

    private val _myBetResult = MutableLiveData<Event<MyBetsRes?>>()
    val myBetResult: LiveData<Event<MyBetsRes?>> = _myBetResult

    fun callMyBetsDetailsAsync() {
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = myBetsRepository.callMyBetAsync(matchId.toString())) {
                is Results.Success -> handleSuccess(result.data)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    private fun handleSuccess(data: MyBetsRes?) {
        _myBetResult.postValue(Event(data))
    }
}