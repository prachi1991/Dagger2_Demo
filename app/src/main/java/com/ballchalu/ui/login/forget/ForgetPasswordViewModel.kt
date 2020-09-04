package com.ballchalu.ui.login.forget

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.R
import com.ballchalu.base.BaseViewModel
import com.ballchalu.shared.core.exception.NotFoundException
import com.ballchalu.shared.core.result.Event
import com.ballchalu.shared.core.result.Results
import com.ballchalu.shared.domain.ForgetPassRes
import com.ballchalu.shared.domain.data.LoginFormState
import com.ballchalu.shared.network.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForgetPasswordViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    BaseViewModel() {
    private val _loginForm = MutableLiveData<Event<LoginFormState>>()
    val loginFormState: LiveData<Event<LoginFormState>> = _loginForm

    private val _loginResult = MutableLiveData<Event<ForgetPassRes>>()
    val loginResult: LiveData<Event<ForgetPassRes>> = _loginResult

    fun callForgetPassword(email: String) {
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = loginRepository.getForgetPassword(email)) {
                is Results.Success -> handleSuccess(result.data)
                is Results.Error -> handleFailure(result.exception)
            }
            loading.postValue(Event(false))
        }
    }

    private fun handleFailure(exception: Exception) {
        if (exception is NotFoundException)
            failure.postValue(Event("404 Undefined"))
        else
            failure.postValue(Event(exception.message.toString()))
    }

    private fun handleSuccess(result: ForgetPassRes) {
        result.password_token?.isNotEmpty()?.let {
            _loginResult.postValue(Event(result))
        }
    }

    fun validateData(username: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value =
                Event(
                    LoginFormState(
                        usernameError = R.string.invalid_email
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


}
