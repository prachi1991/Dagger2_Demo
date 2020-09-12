package com.ballchalu.ui.razorpay.method.banking

import com.ballchalu.shared.core.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Module where classes needed to create the [NetBankingModule] are defined.
 */
@Module
abstract class NetBankingModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeFragment(): NetBankingFragment

//    @Binds
//    @IntoMap
//    @ViewModelKey(CardViewModel::class)
//    internal abstract fun bindViewModel(viewModel: CardViewModel): ViewModel


}
