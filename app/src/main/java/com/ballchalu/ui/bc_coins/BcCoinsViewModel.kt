package com.ballchalu.ui.bc_coins

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.base.BaseViewModel
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.domain.bccoins.BcCoinContest
import com.ccpp.shared.domain.bccoins.BcCoinRes
import com.ccpp.shared.domain.bccoins.buy.BcCoinBuyRes
import com.ccpp.shared.domain.user.UserData
import com.ccpp.shared.domain.user.UserRes
import com.ccpp.shared.network.repository.BcCoinsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class BcCoinsViewModel @Inject constructor(
    private val sharedPref: SharedPreferenceStorage,
    val repository: BcCoinsRepository
) : BaseViewModel() {
    private val _bcCoinListObserver = MutableLiveData<Event<List<BcCoinContest>>>()
    val bcCoinListObserver: LiveData<Event<List<BcCoinContest>>> = _bcCoinListObserver

    fun callBcCoinsList() {
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.callBcCoinsListAsync()) {
                is Results.Success -> handleSuccess(result.data)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    private fun handleSuccess(result: BcCoinRes) {
        _bcCoinListObserver.postValue(Event(result.contests))
    }

    private val _userDetails = MutableLiveData<Event<UserData?>>()
    var userDetails: MutableLiveData<Event<UserData?>> = _userDetails

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
        sharedPref.userName = data.user?.email ?: ""
        _userDetails.postValue(Event(data.user))
    }

    private val _bcCoinBuyObserver = MutableLiveData<Event<BcCoinBuyRes>>()
    var bcCoinBuyObserver: MutableLiveData<Event<BcCoinBuyRes>> = _bcCoinBuyObserver

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
            _userDetails.postValue(Event(result.bc_coins_transaction?.user))
            _bcCoinBuyObserver.postValue(Event(result))
        }
    }

}