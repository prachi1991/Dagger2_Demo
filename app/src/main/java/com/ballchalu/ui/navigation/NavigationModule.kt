package com.ballchalu.ui.navigation

import androidx.lifecycle.ViewModel
import com.ballchalu.ui.login.LoginViewModel
import com.ballchalu.ui.splash.SplashViewModel
import com.ccpp.shared.core.di.ViewModelKey
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

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    internal abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

}
