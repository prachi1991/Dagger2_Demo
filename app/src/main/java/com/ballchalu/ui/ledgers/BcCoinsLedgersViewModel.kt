package com.ballchalu.ui.ledgers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.base.BaseViewModel
import com.ballchalu.shared.core.result.Event
import com.ballchalu.shared.core.result.Results
import com.ballchalu.shared.domain.bccoins.BcCoinLedgersRes
import com.ballchalu.shared.domain.bccoins.BcCoinsLedgerData
import com.ballchalu.shared.network.repository.BcCoinsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class BcCoinsLedgersViewModel @Inject constructor(private val repository: BcCoinsRepository) :
    BaseViewModel() {
    var isLoading: Boolean = false
    var isLastPage: Boolean = false
    private val _bcCoinsListObserver = MutableLiveData<Event<List<BcCoinsLedgerData>>>()
    val bcCoinsListObserver: LiveData<Event<List<BcCoinsLedgerData>>> = _bcCoinsListObserver

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
        _bcCoinsListObserver.postValue(Event(data.bc_coins_ledgers))
        isLastPage = data.meta.total_pages < page
        isLoading = false
    }

}
