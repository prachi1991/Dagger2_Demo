package com.ballchalu.ui.login.forget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.R
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentForgetPasswordBinding
import com.ballchalu.shared.core.result.EventObserver
import com.ballchalu.shared.util.viewModelProvider
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
            val loginState = it
            if (loginState.usernameError != null) {
                binding.edtEmailValue.error = getString(loginState.usernameError!!)
            }

            if (it.isDataValid) {
                hideSoftKeyBoard()
                viewModel.callForgetPassword(
                    binding.edtEmailValue.text.toString()
                )
            }
        })

        viewModel.failure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
        })

        viewModel.loginResult.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(
                requireContext(),
                getString(R.string.message_password_instruction),
                Toast.LENGTH_LONG
            ).show()
            requireActivity().onBackPressed()
        })

        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            binding.btnSignUp.isEnabled = !it
        })

        binding.btnSignUp.setOnClickListener {
            viewModel.validateData(
                binding.edtEmailValue.text.toString()
            )
        }
    }
}