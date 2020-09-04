package com.ballchalu.ui.profile.details

import androidx.lifecycle.ViewModel
import com.ballchalu.shared.core.di.FragmentScoped
import com.ballchalu.shared.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
@Module
internal abstract class ProfileModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeFragment(): ProfileFragment

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    internal abstract fun bindViewModel(viewModel: ProfileViewModel): ViewModel


}
