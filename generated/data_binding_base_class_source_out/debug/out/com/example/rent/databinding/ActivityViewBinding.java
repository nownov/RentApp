// Generated by view binder compiler. Do not edit!
package com.example.rent.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.rent.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityViewBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button btnCheck;

  @NonNull
  public final Button btnRetype;

  @NonNull
  public final ImageView page1;

  @NonNull
  public final ImageView page2;

  @NonNull
  public final ImageView page3;

  @NonNull
  public final ImageView page4;

  @NonNull
  public final ImageView page5;

  @NonNull
  public final ImageView page6;

  @NonNull
  public final ImageView page7;

  private ActivityViewBinding(@NonNull LinearLayout rootView, @NonNull Button btnCheck,
      @NonNull Button btnRetype, @NonNull ImageView page1, @NonNull ImageView page2,
      @NonNull ImageView page3, @NonNull ImageView page4, @NonNull ImageView page5,
      @NonNull ImageView page6, @NonNull ImageView page7) {
    this.rootView = rootView;
    this.btnCheck = btnCheck;
    this.btnRetype = btnRetype;
    this.page1 = page1;
    this.page2 = page2;
    this.page3 = page3;
    this.page4 = page4;
    this.page5 = page5;
    this.page6 = page6;
    this.page7 = page7;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_view, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityViewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_check;
      Button btnCheck = ViewBindings.findChildViewById(rootView, id);
      if (btnCheck == null) {
        break missingId;
      }

      id = R.id.btn_retype;
      Button btnRetype = ViewBindings.findChildViewById(rootView, id);
      if (btnRetype == null) {
        break missingId;
      }

      id = R.id.page1;
      ImageView page1 = ViewBindings.findChildViewById(rootView, id);
      if (page1 == null) {
        break missingId;
      }

      id = R.id.page2;
      ImageView page2 = ViewBindings.findChildViewById(rootView, id);
      if (page2 == null) {
        break missingId;
      }

      id = R.id.page3;
      ImageView page3 = ViewBindings.findChildViewById(rootView, id);
      if (page3 == null) {
        break missingId;
      }

      id = R.id.page4;
      ImageView page4 = ViewBindings.findChildViewById(rootView, id);
      if (page4 == null) {
        break missingId;
      }

      id = R.id.page5;
      ImageView page5 = ViewBindings.findChildViewById(rootView, id);
      if (page5 == null) {
        break missingId;
      }

      id = R.id.page6;
      ImageView page6 = ViewBindings.findChildViewById(rootView, id);
      if (page6 == null) {
        break missingId;
      }

      id = R.id.page7;
      ImageView page7 = ViewBindings.findChildViewById(rootView, id);
      if (page7 == null) {
        break missingId;
      }

      return new ActivityViewBinding((LinearLayout) rootView, btnCheck, btnRetype, page1, page2,
          page3, page4, page5, page6, page7);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
