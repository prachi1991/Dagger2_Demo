package com.ballchalu.razorpay;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/ballchalu/razorpay/Constants;", "", "()V", "AMOUNT", "", "ERROR_CODE", "ERROR_MESSAGE", "PAYMENT_MODEL", "PHONE_PATTERN", "PRODUCT", "RAZOR_PAY_ID", "TYPE_BANKING", "TYPE_CARD", "TYPE_UPI", "TYPE_WALLET", "razorPay_prod"})
public final class Constants {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ERROR_MESSAGE = "error_message";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ERROR_CODE = "error_code";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String RAZOR_PAY_ID = "razor_pay_id";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String AMOUNT = "amount";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PAYMENT_MODEL = "payment_model";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PRODUCT = "product";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TYPE_CARD = "card";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TYPE_WALLET = "wallet";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TYPE_BANKING = "netbanking";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TYPE_UPI = "upi";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PHONE_PATTERN = "^[987]\\d{9}$";
    public static final com.ballchalu.razorpay.Constants INSTANCE = null;
    
    private Constants() {
        super();
    }
}