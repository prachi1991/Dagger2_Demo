package com.ballchalu.razorpay.method.card;

import java.lang.System;

/**
 * Module where classes needed to create the [CardModule] are defined.
 */
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0015\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H!\u00a2\u0006\u0002\b\u0007J\r\u0010\b\u001a\u00020\tH!\u00a2\u0006\u0002\b\n\u00a8\u0006\u000b"}, d2 = {"Lcom/ballchalu/razorpay/method/card/CardModule;", "", "()V", "bindViewModel", "Landroidx/lifecycle/ViewModel;", "viewModel", "Lcom/ballchalu/razorpay/method/card/CardViewModel;", "bindViewModel$razorPay_prod", "contributeFragment", "Lcom/ballchalu/razorpay/method/card/CardFragment;", "contributeFragment$razorPay_prod", "razorPay_prod"})
@dagger.Module()
public abstract class CardModule {
    
    @org.jetbrains.annotations.NotNull()
    @dagger.android.ContributesAndroidInjector()
    @com.ballchalu.shared.core.di.FragmentScoped()
    public abstract com.ballchalu.razorpay.method.card.CardFragment contributeFragment$razorPay_prod();
    
    @org.jetbrains.annotations.NotNull()
    @com.ballchalu.shared.core.di.ViewModelKey(value = com.ballchalu.razorpay.method.card.CardViewModel.class)
    @dagger.multibindings.IntoMap()
    @dagger.Binds()
    public abstract androidx.lifecycle.ViewModel bindViewModel$razorPay_prod(@org.jetbrains.annotations.NotNull()
    com.ballchalu.razorpay.method.card.CardViewModel viewModel);
    
    public CardModule() {
        super();
    }
}