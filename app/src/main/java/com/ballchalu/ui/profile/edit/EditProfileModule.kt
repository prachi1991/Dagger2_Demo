package com.ballchalu.ui.profile.edit

import androidx.lifecycle.ViewModel
import com.ballchalu.ui.profile.menu.ProfileListFragment
import com.ballchalu.ui.profile.menu.ProfileListViewModel
import com.ballchalu.shared.core.di.FragmentScoped
import com.ballchalu.shared.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class EditProfileModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeFragment(): EditProfileFragment

    @Binds
    @IntoMap
    @ViewModelKey(EditProfileViewModel::class)
    internal abstract fun bindViewModel(viewModel: EditProfileViewModel): ViewModel


}
