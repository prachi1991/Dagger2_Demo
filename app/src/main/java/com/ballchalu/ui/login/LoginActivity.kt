package com.ballchalu.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.base.BaseActivity
import com.ballchalu.databinding.ActivityLoginBinding
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject


class LoginActivity : BaseActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModelProvider(viewModelFactory)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        viewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid

            if (loginState.usernameError != null) {
                binding.username.error = getString(loginState.usernameError!!)
            }
            if (loginState.passwordError != null) {
                binding.password.error = getString(loginState.passwordError!!)
            }
            if (it.isDataValid) {
                viewModel.callLogin(binding.username.toString(), binding.password.toString())
            }
        })

        viewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            updateUiWithUser(loginResult.status)
        })

        viewModel.loading.observe(this, Observer {
            binding.progressBar.visibility = if (it.hasBeenHandled) View.VISIBLE else View.GONE
            binding.login.isEnabled = !it.hasBeenHandled
        })
        binding.login.setOnClickListener {
            viewModel.callLogin("", "")
            viewModel.validateData(
                binding.username.text.toString(),
                binding.password.text.toString()
            )
        }
    }

    private fun updateUiWithUser(status: String?) {
        Toast.makeText(applicationContext, "$status", Toast.LENGTH_LONG).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

}
