package com.ballchalu.ui.match.details

import androidx.lifecycle.ViewModel
import com.ccpp.shared.core.di.FragmentScoped
import com.ccpp.shared.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module where classes needed to create the [MatchDetailsFragment] are defined.
 */
@Module
internal abstract class MatchDetailsModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeFragment(): MatchDetailsFragment

    @Binds
    @IntoMap
    @ViewModelKey(MatchDetailsViewModel::class)
    internal abstract fun bindViewModel(viewModel: MatchDetailsViewModel): ViewModel


}
