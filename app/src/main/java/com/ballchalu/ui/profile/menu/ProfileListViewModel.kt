package com.ballchalu.ui.profile.menu

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.R
import com.ballchalu.base.BaseViewModel
import com.ballchalu.utils.Utils
import com.ballchalu.shared.core.result.Event
import com.ballchalu.shared.core.result.Results
import com.ballchalu.shared.database.prefs.SharedPreferenceStorage
import com.ballchalu.shared.domain.user.UserData
import com.ballchalu.shared.domain.user.UserRes
import com.ballchalu.shared.network.repository.LoginRepository
import com.ballchalu.shared.util.ConstantsBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.net.URL
import javax.inject.Inject

class ProfileListViewModel @Inject constructor(
    private val sharePref: SharedPreferenceStorage,
    private val context: Context,
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
        if (data.user?.profileUrl.isNullOrEmpty()) {
            val image = Utils.getDrawableToBitmap(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_user
                )!!
            )
            _loadProfile.postValue(Event(image))
        } else
            data.user?.profileUrl?.let { loadProfile(it) }
    }

    private fun loadProfile(profileUrl: String) {
        try {
            val url = URL(profileUrl)
            val image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            _loadProfile.postValue(Event(image))
        } catch (e: IOException) {
            Timber.e(e)
        }
    }

    private val _loadProfile = MutableLiveData<Event<Bitmap>>()
    var loadProfile: MutableLiveData<Event<Bitmap>> = _loadProfile

    fun getSelectedTheme(): Int {
        when (sharePref.theme) {
            ConstantsBase.THEME_DARK_MODE -> return 0
            ConstantsBase.THEME_LIGHT_MODE -> return 1
            ConstantsBase.THEME_DEFAULT_MODE -> return 2
        }
        return 3
    }
}
