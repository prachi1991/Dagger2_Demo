package com.ballchalu.ui.login

import androidx.lifecycle.ViewModel
import com.ballchalu.ui.login.container.LoginViewModel
import com.ballchalu.ui.login.container.SignInUpContainerFragment
import com.ballchalu.shared.core.di.FragmentScoped
import com.ballchalu.shared.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module where classes needed to create the [LoginModule] are defined.
 */
@Module
internal abstract class LoginModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeFragment(): SignInUpContainerFragment

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun bindViewModel(viewModel: LoginViewModel): ViewModel


}
