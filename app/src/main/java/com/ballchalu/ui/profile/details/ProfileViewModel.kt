package com.ballchalu.ui.profile.details

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.net.URL
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
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

    private val _loadProfile = MutableLiveData<Event<Bitmap>>()
    var loadProfile: MutableLiveData<Event<Bitmap>> = _loadProfile

    fun loadProfileImage() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val url =
                    URL("https://i.pinimg.com/564x/2b/ee/1a/2bee1ac1d0cbcdeb429151feb4627ea3.jpg")
                val image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                _loadProfile.postValue(Event(image))
            } catch (e: IOException) {
                Timber.e(e)
            }
        }
    }
}
