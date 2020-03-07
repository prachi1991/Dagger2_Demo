package com.ballchalu.ui.how_to_play

import androidx.lifecycle.ViewModel
import com.ballchalu.ui.contest.all_contest.ContestFragment
import com.ballchalu.ui.contest.user_contest.UserContestFragment
import com.ccpp.shared.core.di.FragmentScoped
import com.ccpp.shared.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module where classes needed to create the [HowToPlayModule] are defined.
 */
@Module
internal abstract class HowToPlayModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeHowToPlayFragment(): HowToPlayFragment


    @Binds
    @IntoMap
    @ViewModelKey(HowToPlayViewModel::class)
    internal abstract fun bindViewModel(viewModel: HowToPlayViewModel): ViewModel


}
