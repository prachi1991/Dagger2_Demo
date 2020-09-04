package com.ballchalu.ui.winners

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.base.BaseViewModel
import com.ballchalu.shared.core.result.Event
import com.ballchalu.shared.core.result.Results
import com.ballchalu.shared.domain.winner.RanksItem
import com.ballchalu.shared.domain.winner.WinnerRes
import com.ballchalu.shared.network.repository.WinnerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class WinnersViewModel @Inject constructor(val repository: WinnerRepository) : BaseViewModel() {

    private val _winnerListObserver = MutableLiveData<Event<List<RanksItem>?>>()
    val winnerListObserver: LiveData<Event<List<RanksItem>?>> = _winnerListObserver
    var matchId: Int = 0
    var contestId: Int = 0
    var page: Int = 0

    fun calWinnerAsync() {
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.callWinnerListing(matchId, contestId, page)) {
                is Results.Success -> handleSuccess(result.data)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    private fun handleSuccess(data: WinnerRes) {
        _winnerListObserver.postValue(Event(data.ranks))
    }
}