package com.ballchalu.ui.login.signup

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

class SignUpViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    BaseViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<Event<LoginResult>>()
    val loginResult: LiveData<Event<LoginResult>> = _loginResult

    fun callSignUp(username: String, password: String) {
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = loginRepository.getSignUpCall(username, password)) {
                is Results.Success -> _loginResult.postValue(Event(result.data))
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    fun validateData(
        firstName: String,
        lastName: String,
        email: String,
        pass: String,
        confPass: String
    ) {
        when {
            firstName.isEmpty() -> {
                _loginForm.value =
                    LoginFormState(
                        firstName = R.string.invalid_first_name
                    )
            }
            lastName.isEmpty() -> {
                _loginForm.value =
                    LoginFormState(
                        lastName = R.string.invalid_last_name
                    )
            }
            !isUserNameValid(email) -> {
                _loginForm.value =
                    LoginFormState(
                        emailError = R.string.invalid_email
                    )
            }
            !isPasswordValid(pass) -> {
                _loginForm.value =
                    LoginFormState(
                        passwordError = R.string.invalid_password
                    )
            }
            !isPasswordValid(confPass) -> {
                _loginForm.value =
                    LoginFormState(
                        confirmPasswordError = R.string.invalid_password
                    )
            }
            pass != confPass -> {
                _loginForm.value =
                    LoginFormState(
                        confirmPasswordError = R.string.invalid_password_not_match
                    )
            }
            else -> {
                _loginForm.value =
                    LoginFormState(
                        isDataValid = true
                    )
            }
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else false
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    override fun onCleared() {
        super.onCleared()
    }
}