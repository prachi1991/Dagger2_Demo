package com.ballchalu.ui.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.base.BaseViewModel
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.domain.LoginResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class NavigationViewModel @Inject constructor() :
    BaseViewModel() {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun callLogin(username: String, password: String) {
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            //            when (val result = splashRepository.getLoginCall(username, password)) {
//                is Results.Success -> _loginResult.postValue(result.data)
//                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
//            }
            loading.postValue(Event(false))
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}
