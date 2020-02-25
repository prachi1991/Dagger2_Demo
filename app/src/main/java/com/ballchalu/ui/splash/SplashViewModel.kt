package com.ballchalu.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ballchalu.base.BaseViewModel
import com.ballchalu.domain.LoginResult
import com.ballchalu.ui.login.data.LoginFormState
import com.ballchalu.ui.login.data.LoginRepository
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.core.result.Results
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


class SplashViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    BaseViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult
    private var job: Job? = null

    fun callLogin(username: String, password: String) {
        loading.postValue(Event(true))
        job = CoroutineScope(Dispatchers.IO).launch {
            when (val result = loginRepository.getLoginCall(username, password)) {
                is Results.Success -> _loginResult.postValue(result.data)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
