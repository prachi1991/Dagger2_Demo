package com.ballchalu.razorpay.base;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0003\u0004\u0005\u0006B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0004\u0007\b\t\n\u00a8\u0006\u000b"}, d2 = {"Lcom/ballchalu/razorpay/base/Failure;", "", "()V", "FeatureFailure", "NetworkConnection", "ServerError", "SessionError", "Lcom/ballchalu/razorpay/base/Failure$NetworkConnection;", "Lcom/ballchalu/razorpay/base/Failure$ServerError;", "Lcom/ballchalu/razorpay/base/Failure$SessionError;", "Lcom/ballchalu/razorpay/base/Failure$FeatureFailure;", "razorPay_prod"})
public abstract class Failure {
    
    private Failure() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/ballchalu/razorpay/base/Failure$NetworkConnection;", "Lcom/ballchalu/razorpay/base/Failure;", "()V", "razorPay_prod"})
    public static final class NetworkConnection extends com.ballchalu.razorpay.base.Failure {
        
        public NetworkConnection() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/ballchalu/razorpay/base/Failure$ServerError;", "Lcom/ballchalu/razorpay/base/Failure;", "()V", "razorPay_prod"})
    public static final class ServerError extends com.ballchalu.razorpay.base.Failure {
        
        public ServerError() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/ballchalu/razorpay/base/Failure$SessionError;", "Lcom/ballchalu/razorpay/base/Failure;", "()V", "razorPay_prod"})
    public static final class SessionError extends com.ballchalu.razorpay.base.Failure {
        
        public SessionError() {
            super();
        }
    }
    
    /**
     * * Extend this class for feature specific failures.
     */
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/ballchalu/razorpay/base/Failure$FeatureFailure;", "Lcom/ballchalu/razorpay/base/Failure;", "()V", "razorPay_prod"})
    public static abstract class FeatureFailure extends com.ballchalu.razorpay.base.Failure {
        
        public FeatureFailure() {
            super();
        }
    }
}