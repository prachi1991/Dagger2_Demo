package com.ballchalu.ui.login.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentSignUpBinding
import com.ballchalu.ui.navigation.NavigationActivity
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.domain.SignUpReq
import com.ccpp.shared.domain.User
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject


class SignUpFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentSignUpBinding.inflate(inflater).apply {
            lifecycleOwner = this@SignUpFragment
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.loginFormState.observe(viewLifecycleOwner, Observer { loginFormState ->
            val loginState = loginFormState ?: return@Observer

            loginState.firstName?.let {
                binding.edtFirstName.error = getString(it)
            }

            loginState.lastName?.let {
                binding.edtLastNameValue.error = getString(it)
            }

            loginState.emailError?.let {
                binding.edtEmailValue.error = getString(it)
            }

            loginState.usernameError?.let {
                binding.edtUserName.error = getString(it)
            }

            loginState.passwordError?.let {
                binding.edtPasswordValue.error = getString(it)
            }

            loginState.confirmPasswordError?.let {
                binding.edtConfirmPasswordValue.error = getString(it)
            }


            if (loginFormState.isDataValid) {
                viewModel.callSignUp(
                    SignUpReq(
                        User(
                            firstName = binding.edtFirstName.text.toString(),
                            lastName = binding.edtLastNameValue.text.toString(),
                            userName = binding.edtUserName.text.toString(),
                            email = binding.edtEmailValue.text.toString(),
                            password = binding.edtPasswordValue.text.toString(),
                            confirmPassword = binding.edtConfirmPasswordValue.text.toString()
                        )
                    )
                )
            }
        })

        viewModel.loginResult.observe(viewLifecycleOwner, EventObserver {
            startActivity(Intent(requireContext(), NavigationActivity::class.java))
            requireActivity().finish()
            updateUiWithUser("Success")
        })


        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            binding.btnSignUp.isEnabled = !it
        })

        viewModel.failure.observe(viewLifecycleOwner, EventObserver {
            updateUiWithUser(it)
        })

        binding.btnSignUp.setOnClickListener {
            hideSoftKeyBoard()
            viewModel.validateData(
                binding.edtFirstName.text.toString(),
                binding.edtLastNameValue.text.toString(),
                binding.edtUserName.text.toString(),
                binding.edtEmailValue.text.toString(),
                binding.edtPasswordValue.text.toString(),
                binding.edtConfirmPasswordValue.text.toString()
            )
        }

    }

    private fun updateUiWithUser(status: String?) {
        Toast.makeText(requireContext(), "$status", Toast.LENGTH_LONG).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(requireContext(), errorString, Toast.LENGTH_SHORT).show()
    }
}
