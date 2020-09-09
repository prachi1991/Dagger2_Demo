package com.ballchalu.razorpay.method.card;

import com.ballchalu.shared.core.di.FragmentScoped;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = CardModule_ContributeFragment$razorPay_prod.CardFragmentSubcomponent.class)
public abstract class CardModule_ContributeFragment$razorPay_prod {
  private CardModule_ContributeFragment$razorPay_prod() {}

  @Binds
  @IntoMap
  @ClassKey(CardFragment.class)
  abstract AndroidInjector.Factory<?> bindAndroidInjectorFactory(
      CardFragmentSubcomponent.Factory builder);

  @Subcomponent
  @FragmentScoped
  public interface CardFragmentSubcomponent extends AndroidInjector<CardFragment> {
    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<CardFragment> {}
  }
}
