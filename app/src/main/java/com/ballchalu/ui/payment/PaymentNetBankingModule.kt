package com.dl.gc.ui.navigation.ui.gallery

import com.ballchalu.ui.razorpay.method.banking.NetBankingFragment
import com.ballchalu.shared.core.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class PaymentNetBankingModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeHomeFragment(): NetBankingFragment


}
