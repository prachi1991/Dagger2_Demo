package com.ballchalu.ui.login.signin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ballchalu.R
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentSignInBinding
import com.ballchalu.ui.navigation.NavigationActivity
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject


class SignInFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentSignInBinding.inflate(inflater).apply {
            lifecycleOwner = this@SignInFragment
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

        viewModel.loginResult.observe(viewLifecycleOwner, EventObserver {
            val loginResult = it ?: return@EventObserver
            startActivity(Intent(requireContext(), NavigationActivity::class.java))
            requireActivity().finish()
            updateUiWithUser(loginResult.status)
        })

        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            binding.login.isEnabled = !it
        })

        binding.login.setOnClickListener {
            //            viewModel.callLogin("", "")
            viewModel.validateData(
                binding.tvUsernameValue.text.toString(),
                binding.tvPasswordValue.text.toString()
            )
        }
        binding.tvForgetPassword.setOnClickListener {
            findNavController().navigate(R.id.nav_forgetPasswordFragment)
        }


    }

    private fun updateUiWithUser(status: String?) {
        Toast.makeText(requireContext(), "$status", Toast.LENGTH_LONG).show()
    }




}
