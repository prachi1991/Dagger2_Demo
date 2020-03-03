package com.ballchalu.base

import android.content.Context
import android.view.inputmethod.InputMethodManager
import dagger.android.support.DaggerAppCompatActivity
import io.github.inflationx.viewpump.ViewPumpContextWrapper

abstract class BaseActivity : DaggerAppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    fun hideSoftKeyBoard() {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (this.currentFocus != null) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
        }
    }
}