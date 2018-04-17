// Generated code from Butter Knife. Do not modify!
package com.vinh.doctor_x.Fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.vinh.doctor_x.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Frg_fillinfro_doctor_ViewBinding implements Unbinder {
  private Frg_fillinfro_doctor target;

  @UiThread
  public Frg_fillinfro_doctor_ViewBinding(Frg_fillinfro_doctor target, View source) {
    this.target = target;

    target._nameText = Utils.findRequiredViewAsType(source, R.id.input_name, "field '_nameText'", EditText.class);
    target._addressText = Utils.findRequiredViewAsType(source, R.id.input_address, "field '_addressText'", EditText.class);
    target._emailText = Utils.findRequiredViewAsType(source, R.id.input_email, "field '_emailText'", EditText.class);
    target._mobileText = Utils.findRequiredViewAsType(source, R.id.input_mobile, "field '_mobileText'", EditText.class);
    target._passwordText = Utils.findRequiredViewAsType(source, R.id.input_password, "field '_passwordText'", EditText.class);
    target._reEnterPasswordText = Utils.findRequiredViewAsType(source, R.id.input_reEnterPassword, "field '_reEnterPasswordText'", EditText.class);
    target._loginLink = Utils.findRequiredViewAsType(source, R.id.link_login, "field '_loginLink'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Frg_fillinfro_doctor target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._nameText = null;
    target._addressText = null;
    target._emailText = null;
    target._mobileText = null;
    target._passwordText = null;
    target._reEnterPasswordText = null;
    target._loginLink = null;
  }
}
