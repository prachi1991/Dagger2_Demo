package com.ballchalu.ui.profile.edit

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.base.BaseViewModel
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.domain.user.UserData
import com.ccpp.shared.domain.user.UserRes
import com.ccpp.shared.network.repository.LoginRepository
import com.ccpp.shared.util.ConstantsBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.net.URL
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    private val sharePref: SharedPreferenceStorage,
    private val repository: LoginRepository
) : BaseViewModel() {


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
        sharePref.userName = data.user?.email ?: ""
        _userDetails.postValue(Event(data.user))
    }

}
