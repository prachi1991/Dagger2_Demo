package com.ballchalu.razorpay.container;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0015\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H!\u00a2\u0006\u0002\b\u0007\u00a8\u0006\b"}, d2 = {"Lcom/ballchalu/razorpay/container/PaymentSelectionModule;", "", "()V", "bindViewModel", "Landroidx/lifecycle/ViewModel;", "viewModel", "Lcom/ballchalu/razorpay/container/PaymentSelectionViewModel;", "bindViewModel$razorPay_prod", "razorPay_prod"})
@dagger.Module()
public abstract class PaymentSelectionModule {
    
    @org.jetbrains.annotations.NotNull()
    @com.ballchalu.shared.core.di.ViewModelKey(value = com.ballchalu.razorpay.container.PaymentSelectionViewModel.class)
    @dagger.multibindings.IntoMap()
    @dagger.Binds()
    public abstract androidx.lifecycle.ViewModel bindViewModel$razorPay_prod(@org.jetbrains.annotations.NotNull()
    com.ballchalu.razorpay.container.PaymentSelectionViewModel viewModel);
    
    public PaymentSelectionModule() {
        super();
    }
}