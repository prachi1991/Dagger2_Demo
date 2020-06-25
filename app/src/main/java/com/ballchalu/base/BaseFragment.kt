package com.ballchalu.base

import android.content.Context
import android.view.inputmethod.InputMethodManager
import dagger.android.support.DaggerFragment

open class BaseFragment : DaggerFragment() {
    val tAG: String
        get() = this.javaClass.name

    fun hideSoftKeyBoard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }
}