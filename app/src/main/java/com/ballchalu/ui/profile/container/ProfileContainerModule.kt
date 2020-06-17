package com.ballchalu.ui.profile.container

import androidx.lifecycle.ViewModel
import com.ccpp.shared.core.di.FragmentScoped
import com.ccpp.shared.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class ProfileContainerModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeFragment(): ProfileContainerBottomSheet
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(ProfileViewModel::class)
//    internal abstract fun bindViewModel(viewModel: ProfileViewModel): ViewModel


}
