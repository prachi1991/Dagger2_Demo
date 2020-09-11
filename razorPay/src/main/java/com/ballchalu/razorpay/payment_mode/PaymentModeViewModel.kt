package com.ballchalu.razorpay.payment_mode

import androidx.lifecycle.ViewModel
import com.ballchalu.razorpay.base.BaseViewModel
import javax.inject.Inject

class PaymentModeViewModel @Inject constructor(
) : BaseViewModel() {

//    private val _userDetails = MutableLiveData<Event<UserData?>>()
//    var userDetails: MutableLiveData<Event<UserData?>> = _userDetails
//
//    fun callUserDetails() {
//        loading.postValue(Event(true))
//        viewModelScope.launch(Dispatchers.IO) {
//            when (val result = repository.getUserDetails()) {
//                is Results.Success -> handleSuccess(result.data)
//                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
//            }
//            loading.postValue(Event(false))
//        }
//    }
//
//    private fun handleSuccess(data: UserRes) {
//        sharedPref.userName = data.user?.email ?: ""
//        _userDetails.postValue(Event(data.user))
//    }

}