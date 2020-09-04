package com.ballchalu.ui.splash

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ballchalu.base.BaseViewModel
import com.ballchalu.shared.core.result.Event
import com.ballchalu.shared.database.prefs.SharedPreferenceStorage
import com.ballchalu.shared.network.repository.SplashRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import javax.inject.Inject


class SplashViewModel @Inject constructor(
    private val splashRepository: SplashRepository,
    private val sharedPreferenceStorage: SharedPreferenceStorage,
    private val context: Context
) :
    BaseViewModel() {


    private val _loggedInEvent = MutableLiveData<Event<String?>>()
    val loggedInEvent: LiveData<Event<String?>> = _loggedInEvent

    fun checkLogin() {
        GoogleSignIn.getLastSignedInAccount(context)?.let {
            _loggedInEvent.postValue(Event(it.idToken.toString()))
            return
        }
        sharedPreferenceStorage.token?.let {
            _loggedInEvent.postValue(Event(it))
            return
        }

        _loggedInEvent.postValue(Event(null))

    }
}
