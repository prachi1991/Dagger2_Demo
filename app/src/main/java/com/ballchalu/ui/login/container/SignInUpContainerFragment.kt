package com.ballchalu.ui.login.container

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
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject

class SignInUpContainerFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentSignInUpContainerBinding
    private lateinit var viewModel: LoginViewModel
    private val signInFragment by lazy { SignInFragment() }
    private val signOutFragment by lazy { SignInFragment() }

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

}
