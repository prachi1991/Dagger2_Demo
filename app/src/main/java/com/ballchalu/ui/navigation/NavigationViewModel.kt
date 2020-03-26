package com.ballchalu.ui.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.base.BaseViewModel
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.domain.User
import com.ccpp.shared.domain.user.UserData
import com.ccpp.shared.domain.user.UserRes
import com.ccpp.shared.network.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class NavigationViewModel @Inject constructor(
    private val sharedPref: SharedPreferenceStorage,
    private val repository: LoginRepository
) :
    BaseViewModel() {

    private val _logoutResult = MutableLiveData<Event<Boolean>>()
    var logoutResult: MutableLiveData<Event<Boolean>> = _logoutResult

    fun callLogout() {
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            sharedPref.token = ""
            _logoutResult.postValue(Event(true))
            loading.postValue(Event(false))
        }
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

    override fun onCleared() {
        super.onCleared()
    }
}
