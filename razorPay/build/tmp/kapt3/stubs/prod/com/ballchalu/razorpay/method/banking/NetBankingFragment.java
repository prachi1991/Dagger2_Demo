package com.ballchalu.razorpay.method.banking;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\n\u0010\u0014\u001a\u0004\u0018\u00010\u0007H\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u0016H\u0002J\u001c\u0010\u0018\u001a\u00020\u00162\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u001aH\u0016J&\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0016J\u0010\u0010#\u001a\u00020\u00162\u0006\u0010$\u001a\u00020\u0007H\u0002J\u001a\u0010%\u001a\u00020\u00162\u0006\u0010&\u001a\u00020\u001c2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0016J\u0010\u0010\'\u001a\u00020\u00162\u0006\u0010(\u001a\u00020\u000bH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001d\u0010\r\u001a\u0004\u0018\u00010\u000e8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006)"}, d2 = {"Lcom/ballchalu/razorpay/method/banking/NetBankingFragment;", "Lcom/ballchalu/razorpay/base/BaseFragment;", "Lcom/ballchalu/razorpay/method/banking/adapter/NetBankingAdapter$OnClickListener;", "()V", "adapter", "Lcom/ballchalu/razorpay/method/banking/adapter/NetBankingAdapter;", "bankCode", "", "binding", "Lcom/ballchalu/razorpay/databinding/FragmentNetBankingBinding;", "model", "Lcom/ballchalu/razorpay/model/PaymentDetailsModel;", "product", "razorPay", "Lcom/razorpay/Razorpay;", "getRazorPay", "()Lcom/razorpay/Razorpay;", "razorPay$delegate", "Lkotlin/Lazy;", "razorpay", "getAmountFromParent", "initAdapter", "", "initRazor", "onClicked", "pair", "Lkotlin/Pair;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onProcessDetails", "first", "onViewCreated", "view", "sendRequest", "payload", "razorPay_prod"})
public final class NetBankingFragment extends com.ballchalu.razorpay.base.BaseFragment implements com.ballchalu.razorpay.method.banking.adapter.NetBankingAdapter.OnClickListener {
    private com.ballchalu.razorpay.method.banking.adapter.NetBankingAdapter adapter;
    private com.ballchalu.razorpay.model.PaymentDetailsModel model;
    private java.lang.String product;
    @org.jetbrains.annotations.Nullable()
    private final kotlin.Lazy razorPay$delegate = null;
    private java.lang.String bankCode;
    private com.ballchalu.razorpay.databinding.FragmentNetBankingBinding binding;
    private com.razorpay.Razorpay razorpay;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.Nullable()
    public final com.razorpay.Razorpay getRazorPay() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initAdapter() {
    }
    
    private final void initRazor() {
    }
    
    private final void onProcessDetails(java.lang.String first) {
    }
    
    private final void sendRequest(com.ballchalu.razorpay.model.PaymentDetailsModel payload) {
    }
    
    private final java.lang.String getAmountFromParent() {
        return null;
    }
    
    @java.lang.Override()
    public void onClicked(@org.jetbrains.annotations.NotNull()
    kotlin.Pair<java.lang.String, java.lang.String> pair) {
    }
    
    public NetBankingFragment() {
        super();
    }
}