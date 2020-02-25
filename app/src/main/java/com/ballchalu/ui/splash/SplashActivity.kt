package com.ballchalu.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ballchalu.R
import com.ballchalu.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        val secondsDelayed = 10
        Handler().postDelayed({


        }, secondsDelayed.toLong())
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}
