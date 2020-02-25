package com.ballchalu.ui

import androidx.lifecycle.ViewModel
import com.ballchalu.ui.login.ui.LoginViewModel
import com.ccpp.shared.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Module where classes needed to create the [MainFragment] are defined.
 */
@Module
internal abstract class MainModule {

    //    @FragmentScoped
//    @ContributesAndroidInjector
//    internal abstract fun contributeHomeFragment(): HomeFragment
//
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

}
