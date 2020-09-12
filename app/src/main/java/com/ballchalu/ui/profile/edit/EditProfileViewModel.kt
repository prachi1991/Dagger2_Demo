package com.ballchalu.ui.profile.edit

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.base.BaseViewModel
import com.ballchalu.utils.Utils
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.domain.profile.EditProfileReq
import com.ccpp.shared.domain.profile.EditProfileRes
import com.ccpp.shared.domain.user.UserData
import com.ccpp.shared.domain.user.UserRes
import com.ccpp.shared.network.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        sharePref.userEmail = data.user?.email ?: ""
        _userDetails.postValue(Event(data.user))
    }

    fun saveDetails(editProfileReq: EditProfileReq, drawable: Drawable?) {
        val image = Utils.getFileToByte(drawable)
        editProfileReq.image = image
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.callSaveProfile(editProfileReq)) {
                is Results.Success -> handleEditProfileSuccess(result.data)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }
    fun removeProfile(editProfileReq: EditProfileReq) {

        editProfileReq.image = ""
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.removeProfileImage(editProfileReq)) {
                is Results.Success -> handleEditProfileSuccess(result.data)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    private val _editUserDetails = MutableLiveData<Event<EditProfileRes>>()
    var editUserDetails: MutableLiveData<Event<EditProfileRes>> = _editUserDetails

    private fun handleEditProfileSuccess(data: EditProfileRes) {
        if (data.success)
            _editUserDetails.postValue(Event(data))
        else
            failure.postValue(Event(data.message.toString()))
    }

}
