package com.ballchalu.ui.splash

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.base.BaseActivity
import com.ballchalu.databinding.ActivitySplashBinding
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
        viewModel.callLogin("", "")

        val secondsDelayed = 10
        Handler().postDelayed({


        }, secondsDelayed.toLong())
        viewModel.loginResult.observe(this, Observer {
            it.totalResults?.let { it1 ->
                Toast.makeText(this, it1.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}
