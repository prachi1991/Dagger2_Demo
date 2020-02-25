package com.ballchalu.base

import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    val tAG: String
        get() = this.javaClass.name


}