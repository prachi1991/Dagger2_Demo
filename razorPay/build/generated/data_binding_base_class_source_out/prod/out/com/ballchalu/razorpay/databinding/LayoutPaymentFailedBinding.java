// Generated by data binding compiler. Do not edit!
package com.ballchalu.razorpay.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ballchalu.razorpay.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LayoutPaymentFailedBinding extends ViewDataBinding {
  @NonNull
  public final AppCompatButton btnTry;

  @NonNull
  public final ImageView imgBuble;

  @NonNull
  public final LinearLayout imgFailBuble;

  @NonNull
  public final LinearLayout llImage;

  @NonNull
  public final LinearLayout llPolygon;

  @NonNull
  public final TextView tvCancel;

  @NonNull
  public final TextView tvPaymentCompleted;

  @NonNull
  public final TextView tvPaymentFailed;

  protected LayoutPaymentFailedBinding(Object _bindingComponent, View _root, int _localFieldCount,
      AppCompatButton btnTry, ImageView imgBuble, LinearLayout imgFailBuble, LinearLayout llImage,
      LinearLayout llPolygon, TextView tvCancel, TextView tvPaymentCompleted,
      TextView tvPaymentFailed) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnTry = btnTry;
    this.imgBuble = imgBuble;
    this.imgFailBuble = imgFailBuble;
    this.llImage = llImage;
    this.llPolygon = llPolygon;
    this.tvCancel = tvCancel;
    this.tvPaymentCompleted = tvPaymentCompleted;
    this.tvPaymentFailed = tvPaymentFailed;
  }

  @NonNull
  public static LayoutPaymentFailedBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_payment_failed, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutPaymentFailedBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutPaymentFailedBinding>inflateInternal(inflater, R.layout.layout_payment_failed, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutPaymentFailedBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_payment_failed, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutPaymentFailedBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutPaymentFailedBinding>inflateInternal(inflater, R.layout.layout_payment_failed, null, false, component);
  }

  public static LayoutPaymentFailedBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static LayoutPaymentFailedBinding bind(@NonNull View view, @Nullable Object component) {
    return (LayoutPaymentFailedBinding)bind(component, view, R.layout.layout_payment_failed);
  }
}
