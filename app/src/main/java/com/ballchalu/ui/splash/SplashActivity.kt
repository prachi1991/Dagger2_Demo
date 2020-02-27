package com.ballchalu.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.base.BaseActivity
import com.ballchalu.databinding.ActivitySplashBinding
import com.ballchalu.ui.login.container.LoginActivity
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModelProvider(viewModelFactory)
        binding = ActivitySplashBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@SplashActivity
        }
        setContentView(binding.root)

        val secondsDelayed = 10
        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()

        }, secondsDelayed.toLong())
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}
