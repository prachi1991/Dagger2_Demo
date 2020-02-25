package com.ballchalu.base

import dagger.android.support.DaggerFragment

open class BaseFragment : DaggerFragment() {
    val tAG: String
        get() = this.javaClass.name


}