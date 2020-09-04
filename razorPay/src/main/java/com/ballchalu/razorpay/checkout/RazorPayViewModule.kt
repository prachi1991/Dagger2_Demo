package com.ballchalu.razorpay.checkout

import com.ballchalu.shared.core.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RazorPayViewModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeFragment(): RazorPayViewFragment

//    @Binds
//    @IntoMap
//    @ViewModelKey(CardViewModel::class)
//    internal abstract fun bindViewModel(viewModel: CardViewModel): ViewModel


}
