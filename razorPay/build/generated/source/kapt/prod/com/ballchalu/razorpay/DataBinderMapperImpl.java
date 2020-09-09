package com.ballchalu.razorpay;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ballchalu.razorpay.databinding.ActivityMainBindingImpl;
import com.ballchalu.razorpay.databinding.ActivityPayemtSelectionBindingImpl;
import com.ballchalu.razorpay.databinding.ActivitySplashBindingImpl;
import com.ballchalu.razorpay.databinding.FragmentCardBindingImpl;
import com.ballchalu.razorpay.databinding.FragmentNetBankingBindingImpl;
import com.ballchalu.razorpay.databinding.FragmentPaymentModeBindingImpl;
import com.ballchalu.razorpay.databinding.FragmentPaymentSampleBindingImpl;
import com.ballchalu.razorpay.databinding.FragmentRazorPayViewBindingImpl;
import com.ballchalu.razorpay.databinding.ItemNetbankingBindingImpl;
import com.ballchalu.razorpay.databinding.LayoutPaymentCompletedBindingImpl;
import com.ballchalu.razorpay.databinding.LayoutPaymentFailedBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYMAIN = 1;

  private static final int LAYOUT_ACTIVITYPAYEMTSELECTION = 2;

  private static final int LAYOUT_ACTIVITYSPLASH = 3;

  private static final int LAYOUT_FRAGMENTCARD = 4;

  private static final int LAYOUT_FRAGMENTNETBANKING = 5;

  private static final int LAYOUT_FRAGMENTPAYMENTMODE = 6;

  private static final int LAYOUT_FRAGMENTPAYMENTSAMPLE = 7;

  private static final int LAYOUT_FRAGMENTRAZORPAYVIEW = 8;

  private static final int LAYOUT_ITEMNETBANKING = 9;

  private static final int LAYOUT_LAYOUTPAYMENTCOMPLETED = 10;

  private static final int LAYOUT_LAYOUTPAYMENTFAILED = 11;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(11);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ballchalu.razorpay.R.layout.activity_main, LAYOUT_ACTIVITYMAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ballchalu.razorpay.R.layout.activity_payemt_selection, LAYOUT_ACTIVITYPAYEMTSELECTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ballchalu.razorpay.R.layout.activity_splash, LAYOUT_ACTIVITYSPLASH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ballchalu.razorpay.R.layout.fragment_card, LAYOUT_FRAGMENTCARD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ballchalu.razorpay.R.layout.fragment_net_banking, LAYOUT_FRAGMENTNETBANKING);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ballchalu.razorpay.R.layout.fragment_payment_mode, LAYOUT_FRAGMENTPAYMENTMODE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ballchalu.razorpay.R.layout.fragment_payment_sample, LAYOUT_FRAGMENTPAYMENTSAMPLE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ballchalu.razorpay.R.layout.fragment_razor_pay_view, LAYOUT_FRAGMENTRAZORPAYVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ballchalu.razorpay.R.layout.item_netbanking, LAYOUT_ITEMNETBANKING);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ballchalu.razorpay.R.layout.layout_payment_completed, LAYOUT_LAYOUTPAYMENTCOMPLETED);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ballchalu.razorpay.R.layout.layout_payment_failed, LAYOUT_LAYOUTPAYMENTFAILED);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYMAIN: {
          if ("layout/activity_main_0".equals(tag)) {
            return new ActivityMainBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYPAYEMTSELECTION: {
          if ("layout/activity_payemt_selection_0".equals(tag)) {
            return new ActivityPayemtSelectionBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_payemt_selection is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYSPLASH: {
          if ("layout/activity_splash_0".equals(tag)) {
            return new ActivitySplashBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_splash is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTCARD: {
          if ("layout/fragment_card_0".equals(tag)) {
            return new FragmentCardBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_card is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTNETBANKING: {
          if ("layout/fragment_net_banking_0".equals(tag)) {
            return new FragmentNetBankingBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_net_banking is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTPAYMENTMODE: {
          if ("layout/fragment_payment_mode_0".equals(tag)) {
            return new FragmentPaymentModeBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_payment_mode is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTPAYMENTSAMPLE: {
          if ("layout/fragment_payment_sample_0".equals(tag)) {
            return new FragmentPaymentSampleBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_payment_sample is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTRAZORPAYVIEW: {
          if ("layout/fragment_razor_pay_view_0".equals(tag)) {
            return new FragmentRazorPayViewBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_razor_pay_view is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMNETBANKING: {
          if ("layout/item_netbanking_0".equals(tag)) {
            return new ItemNetbankingBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_netbanking is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTPAYMENTCOMPLETED: {
          if ("layout/layout_payment_completed_0".equals(tag)) {
            return new LayoutPaymentCompletedBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_payment_completed is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTPAYMENTFAILED: {
          if ("layout/layout_payment_failed_0".equals(tag)) {
            return new LayoutPaymentFailedBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_payment_failed is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(11);

    static {
      sKeys.put("layout/activity_main_0", com.ballchalu.razorpay.R.layout.activity_main);
      sKeys.put("layout/activity_payemt_selection_0", com.ballchalu.razorpay.R.layout.activity_payemt_selection);
      sKeys.put("layout/activity_splash_0", com.ballchalu.razorpay.R.layout.activity_splash);
      sKeys.put("layout/fragment_card_0", com.ballchalu.razorpay.R.layout.fragment_card);
      sKeys.put("layout/fragment_net_banking_0", com.ballchalu.razorpay.R.layout.fragment_net_banking);
      sKeys.put("layout/fragment_payment_mode_0", com.ballchalu.razorpay.R.layout.fragment_payment_mode);
      sKeys.put("layout/fragment_payment_sample_0", com.ballchalu.razorpay.R.layout.fragment_payment_sample);
      sKeys.put("layout/fragment_razor_pay_view_0", com.ballchalu.razorpay.R.layout.fragment_razor_pay_view);
      sKeys.put("layout/item_netbanking_0", com.ballchalu.razorpay.R.layout.item_netbanking);
      sKeys.put("layout/layout_payment_completed_0", com.ballchalu.razorpay.R.layout.layout_payment_completed);
      sKeys.put("layout/layout_payment_failed_0", com.ballchalu.razorpay.R.layout.layout_payment_failed);
    }
  }
}
