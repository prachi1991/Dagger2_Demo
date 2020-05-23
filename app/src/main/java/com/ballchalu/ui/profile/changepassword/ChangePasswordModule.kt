package com.ballchalu.ui.profile.changepassword

import androidx.lifecycle.ViewModel
import com.ballchalu.ui.match.details.my_bets.MyBetsFragment
import com.ballchalu.ui.match.details.my_bets.MyBetsViewModel
import com.ccpp.shared.core.di.FragmentScoped
import com.ccpp.shared.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module where classes needed to create the [ChangePasswordModule] are defined.
 */
@Module
internal abstract class ChangePasswordModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeFragment(): ChangePasswordFragment

    @Binds
    @IntoMap
    @ViewModelKey(ChangePasswordViewModel::class)
    internal abstract fun bindViewModel(viewModel: ChangePasswordViewModel): ViewModel


}
