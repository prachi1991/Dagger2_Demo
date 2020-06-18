package com.ballchalu.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.BuildConfig
import com.ballchalu.R
import com.ballchalu.base.BaseActivity
import com.ballchalu.databinding.ActivitySplashBinding
import com.ballchalu.ui.login.container.LoginActivity
import com.ballchalu.ui.navigation.NavigationActivity
import com.ccpp.shared.core.result.EventObserver
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
            tvBuildVersion.text = resources.getString(
                R.string.build,
                BuildConfig.VERSION_CODE,
                BuildConfig.ENVIRONMENT
            )

        }
        setContentView(binding.root)
        viewModel.loggedInEvent.observe(this, EventObserver {
            if (!it.isNullOrEmpty()) {
                startActivity(Intent(this, NavigationActivity::class.java))
                finish()
                return@EventObserver
            }
            startActivity(Intent(this, LoginActivity::class.java))
            finish()

        })

        val secondsDelayed = 2000
        Handler().postDelayed({
            viewModel.checkLogin()

        }, secondsDelayed.toLong())


    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}
