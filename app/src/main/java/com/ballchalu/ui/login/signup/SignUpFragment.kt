package com.ballchalu.ui.login.signup

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
        viewModel.loginFormState.observe(viewLifecycleOwner, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both tvUsernameValue / tvPasswordValue is valid

            if (loginState.usernameError != null) {
                binding.tvUsernameValue.error = getString(loginState.usernameError!!)
            }
            if (loginState.passwordError != null) {
                binding.tvPasswordValue.error = getString(loginState.passwordError!!)
            }
            if (it.isDataValid) {
                viewModel.callLogin(
                    binding.tvUsernameValue.toString(),
                    binding.tvPasswordValue.toString()
                )
            }
        })

        viewModel.loginResult.observe(viewLifecycleOwner, Observer {
            val loginResult = it ?: return@Observer

            updateUiWithUser(loginResult.status)
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = if (it.hasBeenHandled) View.VISIBLE else View.GONE
//            binding.login.isEnabled = !it.hasBeenHandled
        })

//        binding.login.setOnClickListener {
        viewModel.callLogin("", "")
//            viewModel.validateData(
//                binding.tvUsernameValue.text.toString(),
//                binding.tvPasswordValue.text.toString()
//            )
//        }

    }

    private fun updateUiWithUser(status: String?) {
        Toast.makeText(requireContext(), "$status", Toast.LENGTH_LONG).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(requireContext(), errorString, Toast.LENGTH_SHORT).show()
    }
}
