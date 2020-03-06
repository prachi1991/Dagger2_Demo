package com.ballchalu.ui.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.base.BaseViewModel
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class NavigationViewModel @Inject constructor(
    private val sharedPref: SharedPreferenceStorage
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

    override fun onCleared() {
        super.onCleared()
    }
}
