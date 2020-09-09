package com.ballchalu.razorpay.method.banking;

import com.ballchalu.shared.core.di.FragmentScoped;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents =
      NetBankingModule_ContributeFragment$razorPay_prod.NetBankingFragmentSubcomponent.class
)
public abstract class NetBankingModule_ContributeFragment$razorPay_prod {
  private NetBankingModule_ContributeFragment$razorPay_prod() {}

  @Binds
  @IntoMap
  @ClassKey(NetBankingFragment.class)
  abstract AndroidInjector.Factory<?> bindAndroidInjectorFactory(
      NetBankingFragmentSubcomponent.Factory builder);

  @Subcomponent
  @FragmentScoped
  public interface NetBankingFragmentSubcomponent extends AndroidInjector<NetBankingFragment> {
    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<NetBankingFragment> {}
  }
}
