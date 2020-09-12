package com.ballchalu.ui.razorpay.container

import androidx.lifecycle.ViewModel
import com.ballchalu.shared.core.di.FragmentScoped
import com.ballchalu.shared.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap


@Module
abstract class PaymentSelectionModule {


    @Binds
    @IntoMap
    @ViewModelKey(PaymentSelectionViewModel::class)
    internal abstract fun bindViewModel(viewModel: PaymentSelectionViewModel): ViewModel


}

