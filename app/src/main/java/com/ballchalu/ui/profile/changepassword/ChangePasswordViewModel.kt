package com.ballchalu.ui.profile.changepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.base.BaseViewModel
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.domain.profile.ChangePasswordReq
import com.ccpp.shared.domain.profile.ChangePasswordRes
import com.ccpp.shared.network.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChangePasswordViewModel @Inject constructor(
    private val sharePref: SharedPreferenceStorage,
    private val repository: ProfileRepository
) : BaseViewModel() {

    private val _changePasswordObserver = MutableLiveData<Event<ChangePasswordRes?>>()
    val changePasswordObserver: LiveData<Event<ChangePasswordRes?>> = _changePasswordObserver

    fun callChangePasswordAsync(changePasswordReq: ChangePasswordReq) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(Event(true))
            when (val result = repository.callChangePasswordAsync(changePasswordReq)) {
                is Results.Success -> handleSuccess(result.data)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    private fun handleSuccess(data: ChangePasswordRes) {
        if (data.accessToken?.isNotEmpty() == true) {
            sharePref.token = data.accessToken
            _changePasswordObserver.postValue(Event(data))
        } else failure.postValue(Event(data.message ?: "Something Wrong..."))
    }

}
