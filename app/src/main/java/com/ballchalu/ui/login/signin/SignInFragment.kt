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
import com.ballchalu.utils.Utils
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject


class SignInFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var mIsShowpass: Boolean = false
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
            val loginState = it
            if (loginState.usernameError != null) {
                binding.tvUsernameValue.error = getString(loginState.usernameError!!)
            }
            if (loginState.passwordError != null) {
                binding.tvPasswordValue.error = getString(loginState.passwordError!!)
            }
            if (it.isDataValid) {
                viewModel.callLogin(
                    binding.tvUsernameValue.text.toString(),
                    binding.tvPasswordValue.text.toString()
                )
            }
        })

        viewModel.loginResult.observe(viewLifecycleOwner, EventObserver {
            it.access_token?.isNotEmpty().let {
                startActivity(Intent(requireContext(), NavigationActivity::class.java))
                requireActivity().finish()
                updateUiWithUser("Success")
            }
        })
        viewModel.failure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
        })
        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            binding.login.isEnabled = !it
        })

        binding.login.setOnClickListener {
            hideSoftKeyBoard()
            viewModel.validateData(
                binding.tvUsernameValue.text.toString(),
                binding.tvPasswordValue.text.toString()
            )
        }
        binding.tvForgetPassword.setOnClickListener {
            findNavController().navigate(R.id.nav_forgetPasswordFragment)
        }
        binding.imgShowHidePassword.setOnClickListener {
            mIsShowpass = !mIsShowpass
            Utils.showPassword(binding.tvPasswordValue, binding.imgShowHidePassword, mIsShowpass)

        }

    }

    private fun updateUiWithUser(status: String?) {
        Toast.makeText(requireContext(), "$status", Toast.LENGTH_LONG).show()
    }


}
