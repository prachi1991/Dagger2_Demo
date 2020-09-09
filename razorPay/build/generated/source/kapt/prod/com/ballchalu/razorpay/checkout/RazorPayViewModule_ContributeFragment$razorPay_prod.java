package com.ballchalu.razorpay.checkout;

import com.ballchalu.shared.core.di.FragmentScoped;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents =
      RazorPayViewModule_ContributeFragment$razorPay_prod.RazorPayViewFragmentSubcomponent.class
)
public abstract class RazorPayViewModule_ContributeFragment$razorPay_prod {
  private RazorPayViewModule_ContributeFragment$razorPay_prod() {}

  @Binds
  @IntoMap
  @ClassKey(RazorPayViewFragment.class)
  abstract AndroidInjector.Factory<?> bindAndroidInjectorFactory(
      RazorPayViewFragmentSubcomponent.Factory builder);

  @Subcomponent
  @FragmentScoped
  public interface RazorPayViewFragmentSubcomponent extends AndroidInjector<RazorPayViewFragment> {
    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<RazorPayViewFragment> {}
  }
}
