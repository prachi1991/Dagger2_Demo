package com.ballchalu.ui.login.signup

import androidx.lifecycle.ViewModel
import com.ccpp.shared.core.di.FragmentScoped
import com.ccpp.shared.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module where classes needed to create the [SignUpModule] are defined.
 */
@Module
internal abstract class SignUpModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSignInFragment(): SignUpFragment

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    internal abstract fun bindViewModel(viewModel: SignUpViewModel): ViewModel


}
