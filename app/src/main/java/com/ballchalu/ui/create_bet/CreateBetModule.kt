package com.ballchalu.ui.create_bet

import androidx.lifecycle.ViewModel
import com.ballchalu.shared.core.di.FragmentScoped
import com.ballchalu.shared.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module where classes needed to create the [CreateBetModule] are defined.
 */
@Module
internal abstract class CreateBetModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeCreateBetFragment(): CreateBetFragment

    @Binds
    @IntoMap
    @ViewModelKey(CreateBetViewModel::class)
    internal abstract fun bindViewModel(viewModel: CreateBetViewModel): ViewModel


}
