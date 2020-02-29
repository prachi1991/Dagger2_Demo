package com.ballchalu.ui.login.container

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.base.BaseActivity
import com.ballchalu.databinding.ActivityLoginBinding
import com.ballchalu.ui.navigation.NavigationActivity
import com.ccpp.shared.util.viewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import javax.inject.Inject


class LoginActivity : BaseActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModelProvider(viewModelFactory)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        GoogleSignIn.getLastSignedInAccount(this)?.let {
            startActivity(Intent(this, NavigationActivity::class.java))
            finish()
        }

    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
