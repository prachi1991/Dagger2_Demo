package com.ballchalu.ui.match.details

import androidx.lifecycle.ViewModel
import com.ballchalu.ui.match.details.my_bets.MyBetsFragment
import com.ballchalu.ui.match.details.my_bets.MyBetsViewModel
import com.ballchalu.shared.core.di.FragmentScoped
import com.ballchalu.shared.core.di.ViewModelKey
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

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeMyBetsFragment(): MyBetsFragment

    @Binds
    @IntoMap
    @ViewModelKey(MyBetsViewModel::class)
    internal abstract fun bindMyBetsViewModel(viewModel: MyBetsViewModel): ViewModel


}
