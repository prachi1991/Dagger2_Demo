package com.ballchalu.ui.contest

import androidx.lifecycle.ViewModel
import com.ccpp.shared.core.di.FragmentScoped
import com.ccpp.shared.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module where classes needed to create the [ContestModule] are defined.
 */
@Module
internal abstract class ContestModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeFragment(): ContestFragment

    @Binds
    @IntoMap
    @ViewModelKey(ContestViewModel::class)
    internal abstract fun bindViewModel(viewModel: ContestViewModel): ViewModel


}
