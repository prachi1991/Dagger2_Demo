package com.ballchalu.ui.login.forget

import androidx.lifecycle.ViewModel
import com.ccpp.shared.core.di.FragmentScoped
import com.ccpp.shared.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module where classes needed to create the [ForgetPasswordModule] are defined.
 */
@Module
internal abstract class ForgetPasswordModule {


    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeFragment(): ForgetPasswordFragment

    @Binds
    @IntoMap
    @ViewModelKey(ForgetPasswordViewModel::class)
    internal abstract fun bindViewModel(viewModel: ForgetPasswordViewModel): ViewModel


}
