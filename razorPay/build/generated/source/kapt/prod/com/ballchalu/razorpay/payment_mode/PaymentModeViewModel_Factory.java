// Generated by Dagger (https://dagger.dev).
package com.ballchalu.razorpay.payment_mode;

import dagger.internal.Factory;

public final class PaymentModeViewModel_Factory implements Factory<PaymentModeViewModel> {
  private static final PaymentModeViewModel_Factory INSTANCE = new PaymentModeViewModel_Factory();

  @Override
  public PaymentModeViewModel get() {
    return new PaymentModeViewModel();
  }

  public static PaymentModeViewModel_Factory create() {
    return INSTANCE;
  }

  public static PaymentModeViewModel newInstance() {
    return new PaymentModeViewModel();
  }
}