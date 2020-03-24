package com.ballchalu.ui.ledgers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.base.BaseViewModel
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.domain.bccoins.BcCoinLedgersRes
import com.ccpp.shared.domain.match_details.MatchDetailsRes
import com.ccpp.shared.network.repository.BcCoinsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class BcCoinsLedgersViewModel @Inject constructor(private val repository: BcCoinsRepository) :
    BaseViewModel() {
    private val _bcCoinsListObserver = MutableLiveData<Event<MatchDetailsRes?>>()
    val bcCoinsListObserver: LiveData<Event<MatchDetailsRes?>> = _bcCoinsListObserver

    var page: Int = 1

    fun callBcCoinsLedgers() {
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.callBcCoinsLedgersAsync(page)) {
                is Results.Success -> handleSuccess(result.data)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    private fun handleSuccess(data: BcCoinLedgersRes) {

    }

}
