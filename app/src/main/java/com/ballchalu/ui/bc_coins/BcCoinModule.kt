package com.ballchalu.ui.bc_coins

import androidx.lifecycle.ViewModel
import com.ballchalu.shared.core.di.FragmentScoped
import com.ballchalu.shared.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module where classes needed to create the [BcCoinModule] are defined.
 */
@Module
internal abstract class BcCoinModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeBcCoinsFragment(): BcCoinsFragment

    @Binds
    @IntoMap
    @ViewModelKey(BcCoinsViewModel::class)
    internal abstract fun bindViewModel(viewModel: BcCoinsViewModel): ViewModel


}
