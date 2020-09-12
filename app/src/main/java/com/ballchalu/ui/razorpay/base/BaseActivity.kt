package com.ballchalu.ui.razorpay.base

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import dagger.android.support.DaggerAppCompatActivity
import io.github.inflationx.viewpump.ViewPumpContextWrapper


abstract class BaseActivity : DaggerAppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    fun hideSoftKeyBoard() {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (this.currentFocus != null) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
        }
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (window?.decorView?.systemUiVisibility != View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
//            window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//            else
            window?.decorView?.systemUiVisibility = 0
        }
    }

    private fun setLightStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags =
                activity.window.decorView.systemUiVisibility // get current flag
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // add LIGHT_STATUS_BAR to flag
            activity.window.decorView.systemUiVisibility = flags
            activity.window.statusBarColor = Color.GRAY // optional
        }
    }

    private fun clearLightStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags =
                activity.window.decorView.systemUiVisibility // get current flag
            flags =
                flags xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // use XOR here for remove LIGHT_STATUS_BAR from flags
            activity.window.decorView.systemUiVisibility = flags
            activity.window.statusBarColor = Color.GREEN // optional
        }
    }
}