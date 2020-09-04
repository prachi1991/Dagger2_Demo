package com.ballchalu.ui.match_listing

import androidx.lifecycle.ViewModel
import com.ballchalu.shared.core.di.FragmentScoped
import com.ballchalu.shared.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module where classes needed to create the [MatchListingModule] are defined.
 */
@Module
internal abstract class MatchListingModule {


    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeMatchListingFragment(): MatchListingFragment

    @Binds
    @IntoMap
    @ViewModelKey(MatchListingViewModel::class)
    internal abstract fun bindMatchListingViewModel(viewModel: MatchListingViewModel): ViewModel


}
