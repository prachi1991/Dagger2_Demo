package com.ballchalu.ui.navigation

import androidx.lifecycle.ViewModel
import com.ballchalu.shared.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Module where classes needed to create the [MainFragment] are defined.
 */
@Module
internal abstract class NavigationModule {

    //    @FragmentScoped
//    @ContributesAndroidInjector
//    internal abstract fun contributeHomeFragment(): HomeFragment
//
    @Binds
    @IntoMap
    @ViewModelKey(NavigationViewModel::class)
    internal abstract fun bindViewModel(viewModel: NavigationViewModel): ViewModel


}
