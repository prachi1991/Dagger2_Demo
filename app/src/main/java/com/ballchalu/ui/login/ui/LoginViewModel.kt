package com.ballchalu.ui.login.ui

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ballchalu.R
import com.ballchalu.base.BaseViewModel
import com.ballchalu.domain.LoginResult
import com.ballchalu.ui.login.data.LoginFormState
import com.ballchalu.ui.login.data.LoginRepository
import com.ballchalu.utils.extension.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : BaseViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult
    private var job: Job? = null

    fun callLogin(username: String, password: String) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            when (val result = loginRepository.getLoginCall(username, password)) {
                is Result.Success -> _loginResult.postValue(result.data)
                is Result.Error -> failure.postValue(result.exception.message)
            }
            loading.postValue(false)
        }
    }

    fun validateData(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value =
                LoginFormState(
                    usernameError = R.string.invalid_username
                )
        } else if (!isPasswordValid(password)) {
            _loginForm.value =
                LoginFormState(
                    passwordError = R.string.invalid_password
                )
        } else {
            _loginForm.value =
                LoginFormState(
                    isDataValid = true
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
        job?.cancel()
    }
}
