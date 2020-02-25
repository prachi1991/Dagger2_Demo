package com.ballchalu.ui.splash

import android.os.Bundle
import android.os.Handler
import com.ballchalu.base.BaseActivity
import com.ballchalu.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@SplashActivity
        }
        setContentView(binding.root)


        val secondsDelayed = 10
        Handler().postDelayed({


        }, secondsDelayed.toLong())
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}
