package com.ballchalu.base

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import dagger.android.support.DaggerFragment


open class BaseFragment : DaggerFragment() {
    val tAG: String
        get() = this.javaClass.name

    fun hideSoftKeyBoard() {
        val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        if (imm.isAcceptingText && getView()?.getRootView()?.getWindowToken() != null) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getView()?.getRootView()?.getWindowToken(), 0)
        }
    }

}