package com.ballchalu.ui.contest

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
 * Module where classes needed to create the [ContestModule] are defined.
 */
@Module
internal abstract class ContestModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeFragment(): ContestFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeUserContestFragment(): UserContestFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeMainContestFragment(): MainContestFragment

    @Binds
    @IntoMap
    @ViewModelKey(ContestViewModel::class)
    internal abstract fun bindViewModel(viewModel: ContestViewModel): ViewModel


}
