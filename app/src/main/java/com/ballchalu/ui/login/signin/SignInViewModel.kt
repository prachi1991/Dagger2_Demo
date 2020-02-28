package com.ballchalu.ui.login.signin

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.R
import com.ballchalu.base.BaseViewModel
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.domain.LoginResult
import com.ccpp.shared.domain.data.LoginFormState
import com.ccpp.shared.network.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    BaseViewModel() {

    private val _loginForm = MutableLiveData<Event<LoginFormState>>()
    val loginFormState: LiveData<Event<LoginFormState>> = _loginForm

    private val _loginResult = MutableLiveData<Event<LoginResult>>()
    val loginResult: LiveData<Event<LoginResult>> = _loginResult

    fun callLogin(username: String, password: String) {
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = loginRepository.getLoginCall(username, password)) {
                is Results.Success -> _loginResult.postValue(Event(result.data))
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    fun loginWithSocial(token: String, socialMedia: String, emailId: String) {
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = loginRepository.getLoginWithSocial(token, socialMedia, emailId)) {
                is Results.Success -> _loginResult.postValue(Event(result.data))
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    fun validateData(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value =
                Event(
                    LoginFormState(
                        usernameError = R.string.invalid_username
                    )
                )
        } else if (!isPasswordValid(password)) {
            _loginForm.value =
                Event(
                    LoginFormState(
                        passwordError = R.string.invalid_password
                    )
                )
        } else {
            _loginForm.value =
                Event(
                    LoginFormState(
                        isDataValid = true
                    )
                )
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    override fun onCleared() {
        super.onCleared()
    }
}
