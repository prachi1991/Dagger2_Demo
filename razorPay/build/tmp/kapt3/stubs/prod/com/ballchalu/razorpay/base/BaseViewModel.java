package com.ballchalu.razorpay.base;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R&\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR&\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00050\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\b\"\u0004\b\u000e\u0010\n\u00a8\u0006\u000f"}, d2 = {"Lcom/ballchalu/razorpay/base/BaseViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "failure", "Landroidx/lifecycle/MutableLiveData;", "Lcom/ballchalu/shared/core/result/Event;", "", "getFailure", "()Landroidx/lifecycle/MutableLiveData;", "setFailure", "(Landroidx/lifecycle/MutableLiveData;)V", "loading", "", "getLoading", "setLoading", "razorPay_prod"})
public class BaseViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<com.ballchalu.shared.core.result.Event<java.lang.String>> failure;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<com.ballchalu.shared.core.result.Event<java.lang.Boolean>> loading;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<com.ballchalu.shared.core.result.Event<java.lang.String>> getFailure() {
        return null;
    }
    
    public final void setFailure(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<com.ballchalu.shared.core.result.Event<java.lang.String>> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<com.ballchalu.shared.core.result.Event<java.lang.Boolean>> getLoading() {
        return null;
    }
    
    public final void setLoading(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<com.ballchalu.shared.core.result.Event<java.lang.Boolean>> p0) {
    }
    
    public BaseViewModel() {
        super();
    }
}