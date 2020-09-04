package com.ballchalu.ui.login.signin

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.R
import com.ballchalu.base.BaseViewModel
import com.ballchalu.shared.core.result.Event
import com.ballchalu.shared.core.result.Results
import com.ballchalu.shared.domain.LoginRes
import com.ballchalu.shared.domain.data.LoginFormState
import com.ballchalu.shared.network.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    BaseViewModel() {
    private val _loginForm = MutableLiveData<Event<LoginFormState>>()
    val loginFormState: LiveData<Event<LoginFormState>> = _loginForm

    private val _loginResult = MutableLiveData<Event<LoginRes>>()
    val loginResult: LiveData<Event<LoginRes>> = _loginResult

    fun callLogin(username: String, password: String) {
        if (loading.value?.getContentIfNotHandled() == false) return
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = loginRepository.getLoginCall(getMapQuery(username, password))) {
                is Results.Success -> _loginResult.postValue(Event(result.data))
                is Results.Error -> failure.postValue(Event("Unauthorised"))
            }
            loading.postValue(Event(false))
        }
    }

    private fun getMapQuery(username: String, password: String) =
        hashMapOf<String, String>().apply {
            put("user_name", username)
            put("password", password)
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

}
