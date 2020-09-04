package com.ballchalu.ui.ledgers

import androidx.lifecycle.ViewModel
import com.ballchalu.shared.core.di.FragmentScoped
import com.ballchalu.shared.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module where classes needed to create the [BcCoinsLedgersModule] are defined.
 */
@Module
internal abstract class BcCoinsLedgersModule {


    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeFragment(): BcCoinsLedgersFragment

    @Binds
    @IntoMap
    @ViewModelKey(BcCoinsLedgersViewModel::class)
    internal abstract fun bindViewModel(viewModel: BcCoinsLedgersViewModel): ViewModel


}
