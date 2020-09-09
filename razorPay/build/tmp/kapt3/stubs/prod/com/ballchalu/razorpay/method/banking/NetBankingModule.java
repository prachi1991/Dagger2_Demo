package com.ballchalu.razorpay.method.banking;

import java.lang.System;

/**
 * Module where classes needed to create the [NetBankingModule] are defined.
 */
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\r\u0010\u0003\u001a\u00020\u0004H!\u00a2\u0006\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/ballchalu/razorpay/method/banking/NetBankingModule;", "", "()V", "contributeFragment", "Lcom/ballchalu/razorpay/method/banking/NetBankingFragment;", "contributeFragment$razorPay_prod", "razorPay_prod"})
@dagger.Module()
public abstract class NetBankingModule {
    
    @org.jetbrains.annotations.NotNull()
    @dagger.android.ContributesAndroidInjector()
    @com.ballchalu.shared.core.di.FragmentScoped()
    public abstract com.ballchalu.razorpay.method.banking.NetBankingFragment contributeFragment$razorPay_prod();
    
    public NetBankingModule() {
        super();
    }
}