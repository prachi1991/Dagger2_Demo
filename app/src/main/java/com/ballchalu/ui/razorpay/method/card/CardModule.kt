package com.ballchalu.ui.razorpay.method.card

import androidx.lifecycle.ViewModel
import com.ballchalu.shared.core.di.FragmentScoped
import com.ballchalu.shared.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module where classes needed to create the [CardModule] are defined.
 */
@Module
abstract class CardModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeFragment(): CardFragment

    @Binds
    @IntoMap
    @ViewModelKey(CardViewModel::class)
    internal abstract fun bindViewModel(viewModel: CardViewModel): ViewModel


}
