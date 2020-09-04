package com.ballchalu.ui.match_listing.recent

import androidx.lifecycle.ViewModel
import com.ballchalu.shared.core.di.FragmentScoped
import com.ballchalu.shared.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module where classes needed to create the [DeclaredMatchModule] are defined.
 */
@Module
internal abstract class DeclaredMatchModule {


    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeFragment(): DeclaredMatchFragment

    @Binds
    @IntoMap
    @ViewModelKey(DeclaredMatchViewModel::class)
    internal abstract fun bindViewModel(viewModel: DeclaredMatchViewModel): ViewModel


}
