package com.ballchalu.razorpay.method.card

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class CardViewModel @Inject constructor(
) : ViewModel() {

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