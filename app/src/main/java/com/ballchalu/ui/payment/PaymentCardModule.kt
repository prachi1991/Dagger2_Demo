package com.dl.gc.ui.navigation.ui.gallery

import com.ballchalu.razorpay.method.card.CardFragment
import com.ballchalu.razorpay.payment_mode.PaymentModeModule
import com.ballchalu.shared.core.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class PaymentCardModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeHomeFragment(): CardFragment


}
