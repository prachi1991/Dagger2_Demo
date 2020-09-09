package com.ballchalu.razorpay.checkout.model;

import java.lang.System;

@kotlinx.android.parcel.Parcelize()
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\fJ&\u0010\u0012\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001\u00a2\u0006\u0002\u0010\u0013J\t\u0010\u0014\u001a\u00020\u0005H\u00d6\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u00d6\u0003J\t\u0010\u0019\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001J\u0019\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0005H\u00d6\u0001R \u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\"\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0010\n\u0002\u0010\u000f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006!"}, d2 = {"Lcom/ballchalu/razorpay/checkout/model/RazorPayError;", "Landroid/os/Parcelable;", "error", "Lcom/ballchalu/razorpay/checkout/model/Error;", "httpStatusCode", "", "(Lcom/ballchalu/razorpay/checkout/model/Error;Ljava/lang/Integer;)V", "getError", "()Lcom/ballchalu/razorpay/checkout/model/Error;", "setError", "(Lcom/ballchalu/razorpay/checkout/model/Error;)V", "getHttpStatusCode", "()Ljava/lang/Integer;", "setHttpStatusCode", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "component1", "component2", "copy", "(Lcom/ballchalu/razorpay/checkout/model/Error;Ljava/lang/Integer;)Lcom/ballchalu/razorpay/checkout/model/RazorPayError;", "describeContents", "equals", "", "other", "", "hashCode", "toString", "", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "razorPay_prod"})
public final class RazorPayError implements android.os.Parcelable {
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "error")
    private com.ballchalu.razorpay.checkout.model.Error error;
    @org.jetbrains.annotations.Nullable()
    @com.google.gson.annotations.SerializedName(value = "http_status_code")
    private java.lang.Integer httpStatusCode;
    public static final android.os.Parcelable.Creator CREATOR = null;
    
    @org.jetbrains.annotations.Nullable()
    public final com.ballchalu.razorpay.checkout.model.Error getError() {
        return null;
    }
    
    public final void setError(@org.jetbrains.annotations.Nullable()
    com.ballchalu.razorpay.checkout.model.Error p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getHttpStatusCode() {
        return null;
    }
    
    public final void setHttpStatusCode(@org.jetbrains.annotations.Nullable()
    java.lang.Integer p0) {
    }
    
    public RazorPayError(@org.jetbrains.annotations.Nullable()
    com.ballchalu.razorpay.checkout.model.Error error, @org.jetbrains.annotations.Nullable()
    java.lang.Integer httpStatusCode) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.ballchalu.razorpay.checkout.model.Error component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.ballchalu.razorpay.checkout.model.RazorPayError copy(@org.jetbrains.annotations.Nullable()
    com.ballchalu.razorpay.checkout.model.Error error, @org.jetbrains.annotations.Nullable()
    java.lang.Integer httpStatusCode) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object p0) {
        return false;
    }
    
    @java.lang.Override()
    public int describeContents() {
        return 0;
    }
    
    @java.lang.Override()
    public void writeToParcel(@org.jetbrains.annotations.NotNull()
    android.os.Parcel parcel, int flags) {
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 3)
    public static final class Creator implements android.os.Parcelable.Creator {
        
        public Creator() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.Object[] newArray(int size) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.Object createFromParcel(@org.jetbrains.annotations.NotNull()
        android.os.Parcel in) {
            return null;
        }
    }
}