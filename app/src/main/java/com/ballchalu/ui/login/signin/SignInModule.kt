package com.ballchalu.ui.login.signin

import androidx.lifecycle.ViewModel
import com.ccpp.shared.core.di.FragmentScoped
import com.ccpp.shared.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module where classes needed to create the [SignInModule] are defined.
 */
@Module
internal abstract class SignInModule {


    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSignInFragment(): SignInFragment

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    internal abstract fun bindSignInViewModel(viewModel: SignInViewModel): ViewModel


}
