package com.ballchalu.ui.login.forget

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.R
import com.ballchalu.base.BaseViewModel
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.domain.ForgetPassRes
import com.ccpp.shared.domain.data.LoginFormState
import com.ccpp.shared.network.repository.LoginRepository
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
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
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
