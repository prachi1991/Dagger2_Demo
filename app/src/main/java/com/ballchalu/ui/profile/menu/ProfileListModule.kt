package com.ballchalu.ui.profile.menu

import androidx.lifecycle.ViewModel
import com.ccpp.shared.core.di.FragmentScoped
import com.ccpp.shared.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class ProfileListModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeFragment(): ProfileListFragment

    @Binds
    @IntoMap
    @ViewModelKey(ProfileListViewModel::class)
    internal abstract fun bindViewModel(viewModel: ProfileListViewModel): ViewModel


}
