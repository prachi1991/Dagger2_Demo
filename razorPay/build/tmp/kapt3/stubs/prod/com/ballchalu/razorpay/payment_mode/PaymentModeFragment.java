package com.ballchalu.razorpay.payment_mode;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001:\u00019B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bJ\b\u0010\u001c\u001a\u0004\u0018\u00010\nJ\b\u0010\u001d\u001a\u00020\u0019H\u0002J\b\u0010\u001e\u001a\u00020\u0019H\u0002J\u0012\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\nH\u0002J\u000e\u0010\"\u001a\u00020 2\u0006\u0010#\u001a\u00020\nJ\"\u0010$\u001a\u00020\u00192\u0006\u0010%\u001a\u00020\u00102\u0006\u0010&\u001a\u00020\u00102\b\u0010\'\u001a\u0004\u0018\u00010(H\u0016J&\u0010)\u001a\u0004\u0018\u00010*2\u0006\u0010+\u001a\u00020,2\b\u0010-\u001a\u0004\u0018\u00010.2\b\u0010/\u001a\u0004\u0018\u00010\u001bH\u0016J\b\u00100\u001a\u00020\u0019H\u0016J\b\u00101\u001a\u00020\u0019H\u0016J\u001a\u00102\u001a\u00020\u00192\u0006\u00103\u001a\u00020*2\b\u0010/\u001a\u0004\u0018\u00010\u001bH\u0016J\u0006\u00104\u001a\u00020\u0019J\b\u00105\u001a\u00020\u0019H\u0002J\u0010\u00106\u001a\u00020\u00192\u0006\u00107\u001a\u00020*H\u0002J\b\u00108\u001a\u00020\u0019H\u0002R\u0014\u0010\u0003\u001a\b\u0018\u00010\u0004R\u00020\u0000X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0012\"\u0004\b\u0017\u0010\u0014\u00a8\u0006:"}, d2 = {"Lcom/ballchalu/razorpay/payment_mode/PaymentModeFragment;", "Landroidx/fragment/app/Fragment;", "()V", "adapter", "Lcom/ballchalu/razorpay/payment_mode/PaymentModeFragment$ViewPagerAdapter;", "binding", "Lcom/ballchalu/razorpay/databinding/FragmentPaymentModeBinding;", "disposable", "Lio/reactivex/disposables/Disposable;", "price", "", "getPrice", "()Ljava/lang/String;", "setPrice", "(Ljava/lang/String;)V", "rotationAngle", "", "getRotationAngle", "()I", "setRotationAngle", "(I)V", "rotationAnglePayment", "getRotationAnglePayment", "setRotationAnglePayment", "callBack", "", "bundle", "Landroid/os/Bundle;", "getAmount", "hideContestLayout", "initTabLayout", "isValidEmail", "", "strEmail", "isValidPhone", "phone", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "onDestroy", "onDestroyView", "onViewCreated", "view", "setBackgroundCard", "setPaymentLayoutVissible", "setselection", "btn", "setupViewPager", "ViewPagerAdapter", "razorPay_prod"})
public final class PaymentModeFragment extends androidx.fragment.app.Fragment {
    private com.ballchalu.razorpay.payment_mode.PaymentModeFragment.ViewPagerAdapter adapter;
    private com.ballchalu.razorpay.databinding.FragmentPaymentModeBinding binding;
    private int rotationAngle;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String price;
    private int rotationAnglePayment;
    private io.reactivex.disposables.Disposable disposable;
    private java.util.HashMap _$_findViewCache;
    
    public final int getRotationAngle() {
        return 0;
    }
    
    public final void setRotationAngle(int p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPrice() {
        return null;
    }
    
    public final void setPrice(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    public final int getRotationAnglePayment() {
        return 0;
    }
    
    public final void setRotationAnglePayment(int p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    public final void setBackgroundCard() {
    }
    
    public final void callBack(@org.jetbrains.annotations.NotNull()
    android.os.Bundle bundle) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAmount() {
        return null;
    }
    
    private final void setselection(android.view.View btn) {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
    
    private final void setPaymentLayoutVissible() {
    }
    
    private final void hideContestLayout() {
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initTabLayout() {
    }
    
    @java.lang.Override()
    public void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    private final void setupViewPager() {
    }
    
    private final boolean isValidEmail(java.lang.String strEmail) {
        return false;
    }
    
    public final boolean isValidPhone(@org.jetbrains.annotations.NotNull()
    java.lang.String phone) {
        return false;
    }
    
    public PaymentModeFragment() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\r\n\u0000\b\u0080\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\tJ\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u000fH\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0011\u001a\u00020\u000fH\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/ballchalu/razorpay/payment_mode/PaymentModeFragment$ViewPagerAdapter;", "Landroidx/fragment/app/FragmentPagerAdapter;", "manager", "Landroidx/fragment/app/FragmentManager;", "(Lcom/ballchalu/razorpay/payment_mode/PaymentModeFragment;Landroidx/fragment/app/FragmentManager;)V", "mFragmentList", "Ljava/util/ArrayList;", "Landroidx/fragment/app/Fragment;", "mFragmentTitleList", "", "addFragment", "", "fragment", "title", "getCount", "", "getItem", "position", "getPageTitle", "", "razorPay_prod"})
    public final class ViewPagerAdapter extends androidx.fragment.app.FragmentPagerAdapter {
        private final java.util.ArrayList<androidx.fragment.app.Fragment> mFragmentList = null;
        private final java.util.ArrayList<java.lang.String> mFragmentTitleList = null;
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public androidx.fragment.app.Fragment getItem(int position) {
            return null;
        }
        
        @java.lang.Override()
        public int getCount() {
            return 0;
        }
        
        public final void addFragment(@org.jetbrains.annotations.NotNull()
        androidx.fragment.app.Fragment fragment, @org.jetbrains.annotations.NotNull()
        java.lang.String title) {
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public java.lang.CharSequence getPageTitle(int position) {
            return null;
        }
        
        public ViewPagerAdapter(@org.jetbrains.annotations.NotNull()
        androidx.fragment.app.FragmentManager manager) {
            super(null);
        }
    }
}