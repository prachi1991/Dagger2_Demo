package com.ballchalu.razorpay.payment_mode;

import com.ballchalu.shared.core.di.FragmentScoped;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents =
      PaymentModeModule_ContributeHomeFragment$razorPay_prod.PaymentModeFragmentSubcomponent.class
)
public abstract class PaymentModeModule_ContributeHomeFragment$razorPay_prod {
  private PaymentModeModule_ContributeHomeFragment$razorPay_prod() {}

  @Binds
  @IntoMap
  @ClassKey(PaymentModeFragment.class)
  abstract AndroidInjector.Factory<?> bindAndroidInjectorFactory(
      PaymentModeFragmentSubcomponent.Factory builder);

  @Subcomponent
  @FragmentScoped
  public interface PaymentModeFragmentSubcomponent extends AndroidInjector<PaymentModeFragment> {
    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<PaymentModeFragment> {}
  }
}
