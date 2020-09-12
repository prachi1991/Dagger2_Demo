package com.ballchalu.razorpay.container;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u001b\u001a\u00020\u001cJ\u0006\u0010\u001d\u001a\u00020\u001eJ\u000e\u0010\u001f\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020\u0006J\u0012\u0010!\u001a\u00020\u001e2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0014J\b\u0010$\u001a\u00020\u001eH\u0014J\u0010\u0010%\u001a\u00020\u001e2\b\u0010&\u001a\u0004\u0018\u00010\u0006J\u0006\u0010\'\u001a\u00020\u001eJ\b\u0010(\u001a\u00020\u001eH\u0014J\b\u0010)\u001a\u00020*H\u0016J\u0006\u0010+\u001a\u00020\u001eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u001d\u0010\r\u001a\u0004\u0018\u00010\u000e8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0015\u001a\u00020\u00168\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a\u00a8\u0006,"}, d2 = {"Lcom/ballchalu/razorpay/container/PaymentSelectionActivity;", "Lcom/ballchalu/razorpay/base/BaseActivity;", "()V", "binding", "Lcom/ballchalu/razorpay/databinding/ActivityPayemtSelectionBinding;", "errorDesc", "", "getErrorDesc", "()Ljava/lang/String;", "setErrorDesc", "(Ljava/lang/String;)V", "navController", "Landroidx/navigation/NavController;", "razorPay", "Lcom/razorpay/Razorpay;", "getRazorPay", "()Lcom/razorpay/Razorpay;", "razorPay$delegate", "Lkotlin/Lazy;", "viewModel", "Lcom/ballchalu/razorpay/container/PaymentSelectionViewModel;", "viewModelFactory", "Landroidx/lifecycle/ViewModelProvider$Factory;", "getViewModelFactory", "()Landroidx/lifecycle/ViewModelProvider$Factory;", "setViewModelFactory", "(Landroidx/lifecycle/ViewModelProvider$Factory;)V", "getBackBtn", "Landroid/widget/ImageView;", "getBcCoinData", "", "getError", "desc", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onPaymentFailure", "data", "onPaymentSuccess", "onStart", "onSupportNavigateUp", "", "setCallBack", "razorPay_prod"})
public final class PaymentSelectionActivity extends com.ballchalu.razorpay.base.BaseActivity {
    @org.jetbrains.annotations.Nullable()
    private java.lang.String errorDesc;
    private com.ballchalu.razorpay.databinding.ActivityPayemtSelectionBinding binding;
    @org.jetbrains.annotations.Nullable()
    private final kotlin.Lazy razorPay$delegate = null;
    private androidx.navigation.NavController navController;
    private com.ballchalu.razorpay.container.PaymentSelectionViewModel viewModel;
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Inject()
    public androidx.lifecycle.ViewModelProvider.Factory viewModelFactory;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getErrorDesc() {
        return null;
    }
    
    public final void setErrorDesc(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.razorpay.Razorpay getRazorPay() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.ViewModelProvider.Factory getViewModelFactory() {
        return null;
    }
    
    public final void setViewModelFactory(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.ViewModelProvider.Factory p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    public final void getError(@org.jetbrains.annotations.NotNull()
    java.lang.String desc) {
    }
    
    @java.lang.Override()
    protected void onStart() {
    }
    
    public final void getBcCoinData() {
    }
    
    public final void setCallBack() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.widget.ImageView getBackBtn() {
        return null;
    }
    
    @java.lang.Override()
    public boolean onSupportNavigateUp() {
        return false;
    }
    
    public final void onPaymentFailure(@org.jetbrains.annotations.Nullable()
    java.lang.String data) {
    }
    
    public final void onPaymentSuccess() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    public PaymentSelectionActivity() {
        super();
    }
}