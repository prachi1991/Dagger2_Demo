package com.ballchalu.ui.login.container

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.R
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentSignInUpContainerBinding
import com.ballchalu.ui.login.signin.SignInFragment
import com.ballchalu.ui.login.signup.SignUpFragment
import com.ballchalu.utils.AppConstants
import com.ballchalu.shared.util.viewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import javax.inject.Inject

class SignInUpContainerFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentSignInUpContainerBinding
    private lateinit var viewModel: LoginViewModel
    private val signInFragment by lazy { SignInFragment() }
    private val signOutFragment by lazy { SignUpFragment() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentSignInUpContainerBinding.inflate(inflater).apply {
            lifecycleOwner = this@SignInUpContainerFragment
        }

        binding.rbGroup.setOnCheckedChangeListener { group, checkedId ->
            run {
                when (checkedId) {
                    R.id.rbSignIn -> showFragment(signInFragment)
                    R.id.rbSignUp -> showFragment(signOutFragment)
                }
            }
        }
        showFragment(signInFragment)


        return binding.root
    }

    private fun showFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().replace(R.id.fragmentSign, fragment)
            .commitAllowingStateLoss()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.initGoogleAndFB()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        viewModel.callbackManager?.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppConstants.GOOGLE_SIGN_IN_REQUEST) {
            viewModel.handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data))
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
