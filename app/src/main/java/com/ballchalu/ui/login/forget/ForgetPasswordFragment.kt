package com.ballchalu.ui.login.forget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentForgetPasswordBinding
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject

class ForgetPasswordFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentForgetPasswordBinding
    private lateinit var viewModel: ForgetPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentForgetPasswordBinding.inflate(inflater).apply {
            lifecycleOwner = this@ForgetPasswordFragment
        }


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.loginFormState.observe(viewLifecycleOwner, EventObserver {
            val loginState = it ?: return@EventObserver
            if (loginState.usernameError != null) {
                binding.tvUsernameValue.error = getString(loginState.usernameError!!)
            }

            if (it.isDataValid) {
                viewModel.callForgetPassword(
                    binding.tvUsernameValue.toString()
                )
            }
        })

        viewModel.loginResult.observe(viewLifecycleOwner, EventObserver {
            val loginResult = it ?: return@EventObserver
            activity?.onBackPressed()
        })

        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            binding.btnSignUp.isEnabled = !it
        })

        binding.btnSignUp.setOnClickListener {
            viewModel.validateData(
                binding.tvUsernameValue.text.toString()
            )
        }
    }
}