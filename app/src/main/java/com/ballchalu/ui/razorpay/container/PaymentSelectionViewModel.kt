package com.ballchalu.ui.razorpay.container

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.ui.razorpay.base.BaseViewModel
import com.ballchalu.shared.core.result.Event
import com.ballchalu.shared.core.result.Results
import com.ballchalu.shared.domain.bccoins.BcCoinContest
import com.ballchalu.shared.domain.bccoins.buy.BcCoinBuyRes
import com.ballchalu.shared.domain.user.UserData
import com.ballchalu.shared.domain.user.UserRes
import com.ballchalu.shared.network.repository.BcCoinsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PaymentSelectionViewModel @Inject constructor(
    val repository: BcCoinsRepository
) : BaseViewModel() {
    private val _bcCoinBuyObserver = MutableLiveData<Event<BcCoinBuyRes>>()
    var bcCoinBuyObserver: MutableLiveData<Event<BcCoinBuyRes>> = _bcCoinBuyObserver
   var bccoin: BcCoinContest?=null
    private val _userDetails = MutableLiveData<Event<UserData?>>()
    var userDetails: MutableLiveData<Event<UserData?>> = _userDetails
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

    fun callUserDetails() {
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getUserDetails()) {
                is Results.Success -> handleSuccess(result.data)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }
    private fun handleSuccess(data: UserRes) {

        _userDetails.postValue(Event(data.user))
    }

    private fun handleSuccess(result: BcCoinBuyRes) {
        result.bc_coins_transaction?.id?.let {
            _bcCoinBuyObserver.postValue(Event(result))
        }
    }
}