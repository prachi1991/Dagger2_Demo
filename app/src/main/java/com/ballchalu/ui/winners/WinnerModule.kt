package com.ballchalu.ui.winners

import androidx.lifecycle.ViewModel
import com.ccpp.shared.core.di.FragmentScoped
import com.ccpp.shared.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module where classes needed to create the [WinnerModule] are defined.
 */
@Module
internal abstract class WinnerModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeWinnersFragment(): WinnersFragment

    @Binds
    @IntoMap
    @ViewModelKey(WinnersViewModel::class)
    internal abstract fun bindViewModel(viewModel: WinnersViewModel): ViewModel


}
