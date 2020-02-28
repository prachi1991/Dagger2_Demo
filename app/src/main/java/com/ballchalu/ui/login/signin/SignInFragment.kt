package com.ballchalu.ui.login.signin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentSignInBinding
import com.ballchalu.ui.navigation.NavigationActivity
import com.ballchalu.utils.AppConstants
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.util.viewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
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

        val account = GoogleSignIn.getLastSignedInAccount(requireContext())

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.initGoogleAndFB()

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

        binding.llGoogleSignIn.setOnClickListener {
            startGoogleSignIn()
        }
        binding.llFacebookSignIn.setOnClickListener {
            viewModel.initFacebook(this)
        }
    }

    private fun startGoogleSignIn() {
        val signInIntent = viewModel.mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, AppConstants.GOOGLE_SIGN_IN_REQUEST)
    }



    private fun updateUiWithUser(status: String?) {
        Toast.makeText(requireContext(), "$status", Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        viewModel.callbackManager?.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.GOOGLE_SIGN_IN_REQUEST) {
            viewModel.handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data))
        }
        super.onActivityResult(requestCode, resultCode, data)
    }



}
