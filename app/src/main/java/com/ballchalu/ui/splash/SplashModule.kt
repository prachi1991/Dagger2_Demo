package com.ballchalu.ui.splash

import androidx.lifecycle.ViewModel
import com.ccpp.shared.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Module where classes needed to create the [MainFragment] are defined.
 */
@Module
internal abstract class SplashModule {

    //    @FragmentScoped
//    @ContributesAndroidInjector
//    internal abstract fun contributeHomeFragment(): HomeFragment
//
    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    internal abstract fun bindViewModel(viewModel: SplashViewModel): ViewModel


}
