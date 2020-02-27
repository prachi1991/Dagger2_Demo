package com.ballchalu.ui.login.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentSignInUpContainerBinding
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject

class SignInUpContainerFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var binding: FragmentSignInUpContainerBinding? = null
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentSignInUpContainerBinding.inflate(inflater).apply {
            lifecycleOwner = this@SignInUpContainerFragment
        }






        return binding?.root
    }

}
