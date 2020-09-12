package com.ballchalu.razorpay.container;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000fJ\u0010\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\bH\u0002R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R&\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\u001b"}, d2 = {"Lcom/ballchalu/razorpay/container/PaymentSelectionViewModel;", "Lcom/ballchalu/razorpay/base/BaseViewModel;", "repository", "Lcom/ballchalu/shared/network/repository/BcCoinsRepository;", "(Lcom/ballchalu/shared/network/repository/BcCoinsRepository;)V", "_bcCoinBuyObserver", "Landroidx/lifecycle/MutableLiveData;", "Lcom/ballchalu/shared/core/result/Event;", "Lcom/ballchalu/shared/domain/bccoins/buy/BcCoinBuyRes;", "bcCoinBuyObserver", "getBcCoinBuyObserver", "()Landroidx/lifecycle/MutableLiveData;", "setBcCoinBuyObserver", "(Landroidx/lifecycle/MutableLiveData;)V", "bccoin", "Lcom/ballchalu/shared/domain/bccoins/BcCoinContest;", "getBccoin", "()Lcom/ballchalu/shared/domain/bccoins/BcCoinContest;", "setBccoin", "(Lcom/ballchalu/shared/domain/bccoins/BcCoinContest;)V", "getRepository", "()Lcom/ballchalu/shared/network/repository/BcCoinsRepository;", "callBuyNow", "", "bcCoinContest", "handleSuccess", "result", "razorPay_prod"})
public final class PaymentSelectionViewModel extends com.ballchalu.razorpay.base.BaseViewModel {
    private final androidx.lifecycle.MutableLiveData<com.ballchalu.shared.core.result.Event<com.ballchalu.shared.domain.bccoins.buy.BcCoinBuyRes>> _bcCoinBuyObserver = null;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<com.ballchalu.shared.core.result.Event<com.ballchalu.shared.domain.bccoins.buy.BcCoinBuyRes>> bcCoinBuyObserver;
    @org.jetbrains.annotations.NotNull()
    public com.ballchalu.shared.domain.bccoins.BcCoinContest bccoin;
    @org.jetbrains.annotations.NotNull()
    private final com.ballchalu.shared.network.repository.BcCoinsRepository repository = null;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<com.ballchalu.shared.core.result.Event<com.ballchalu.shared.domain.bccoins.buy.BcCoinBuyRes>> getBcCoinBuyObserver() {
        return null;
    }
    
    public final void setBcCoinBuyObserver(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<com.ballchalu.shared.core.result.Event<com.ballchalu.shared.domain.bccoins.buy.BcCoinBuyRes>> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.ballchalu.shared.domain.bccoins.BcCoinContest getBccoin() {
        return null;
    }
    
    public final void setBccoin(@org.jetbrains.annotations.NotNull()
    com.ballchalu.shared.domain.bccoins.BcCoinContest p0) {
    }
    
    public final void callBuyNow(@org.jetbrains.annotations.NotNull()
    com.ballchalu.shared.domain.bccoins.BcCoinContest bcCoinContest) {
    }
    
    private final void handleSuccess(com.ballchalu.shared.domain.bccoins.buy.BcCoinBuyRes result) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.ballchalu.shared.network.repository.BcCoinsRepository getRepository() {
        return null;
    }
    
    @javax.inject.Inject()
    public PaymentSelectionViewModel(@org.jetbrains.annotations.NotNull()
    com.ballchalu.shared.network.repository.BcCoinsRepository repository) {
        super();
    }
}