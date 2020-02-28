package com.ballchalu.ui.login.signin

import android.util.Patterns
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.R
import com.ballchalu.application.App
import com.ballchalu.base.BaseViewModel
import com.ballchalu.utils.AppConstants
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.domain.LoginResult
import com.ccpp.shared.domain.data.LoginFormState
import com.ccpp.shared.network.repository.LoginRepository
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SignInViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    BaseViewModel() {
    internal var mGoogleSignInClient: GoogleSignInClient? = null
    internal var callbackManager: CallbackManager? = null

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

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        loading.postValue(Event(true))
        try {
            completedTask.getResult(ApiException::class.java)?.let {
                loginWithSocial(
                    it.idToken.toString(),
                    AppConstants.GOOGLE_SIGN_IN,
                    it.email.toString()
                )
            }
        } catch (e: ApiException) { // The ApiException status code indicates the detailed failure reason. // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Timber.d("signInResult:failed code=%s", e.statusCode)
            failure.postValue(Event("Opps Something went wrong " + e.message))
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
        callbackManager = null
    }

    fun initFacebook(context: Fragment) {
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance()
            .logInWithReadPermissions(context, listOf("email", "public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<com.facebook.login.LoginResult?> {
                override fun onSuccess(loginResult: com.facebook.login.LoginResult?) { // App code
                    loginResult?.let {
                        loginWithSocial(
                            it.accessToken.token,
                            AppConstants.FACEBOOK_SIGN_IN,
                            ""
                        )
                    }
                }

                override fun onCancel() { // App code
                    failure.postValue(Event("Opps Something went wrong"))
                }

                override fun onError(exception: FacebookException) { // App code
                    failure.postValue(Event("Opps Something went wrong " + exception.message))
                }
            })
    }

    fun initGoogleAndFB() {
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(App.instance, gso);
    }

}
