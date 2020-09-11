package com.ballchalu.razorpay.container

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.razorpay.base.BaseViewModel
import com.ballchalu.shared.core.result.Event
import com.ballchalu.shared.core.result.Results
import com.ballchalu.shared.domain.bccoins.BcCoinContest
import com.ballchalu.shared.domain.bccoins.buy.BcCoinBuyRes
import com.ballchalu.shared.network.repository.BcCoinsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PaymentSelectionViewModel @Inject constructor(
    val repository: BcCoinsRepository
) : BaseViewModel() {
    private val _bcCoinBuyObserver = MutableLiveData<Event<BcCoinBuyRes>>()
    var bcCoinBuyObserver: MutableLiveData<Event<BcCoinBuyRes>> = _bcCoinBuyObserver
    lateinit var bccoin: BcCoinContest

    fun callBuyNow(bcCoinContest: BcCoinContest) {
        if (bcCoinContest.id == null) return
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.callBuyNowCoins(bcCoinContest.id!!)) {
                is Results.Success -> handleSuccess(result.data)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }


    private fun handleSuccess(result: BcCoinBuyRes) {
        result.bc_coins_transaction?.id?.let {
            _bcCoinBuyObserver.postValue(Event(result))
        }
    }
}