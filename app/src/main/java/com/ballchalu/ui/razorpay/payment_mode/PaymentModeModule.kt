package com.ballchalu.ui.razorpay.payment_mode


import com.ballchalu.shared.core.di.FragmentScoped
import com.ballchalu.ui.razorpay.payment_mode.PaymentModeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PaymentModeModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeHomeFragment(): PaymentModeFragment


}
