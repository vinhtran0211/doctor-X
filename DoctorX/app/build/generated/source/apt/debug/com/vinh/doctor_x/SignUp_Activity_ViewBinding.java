// Generated code from Butter Knife. Do not modify!
package com.vinh.doctor_x;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SignUp_Activity_ViewBinding implements Unbinder {
  private SignUp_Activity target;

  @UiThread
  public SignUp_Activity_ViewBinding(SignUp_Activity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SignUp_Activity_ViewBinding(SignUp_Activity target, View source) {
    this.target = target;

    target._nameText = Utils.findRequiredViewAsType(source, R.id.input_name, "field '_nameText'", EditText.class);
    target._addressText = Utils.findRequiredViewAsType(source, R.id.input_address, "field '_addressText'", EditText.class);
    target._emailText = Utils.findRequiredViewAsType(source, R.id.input_email, "field '_emailText'", EditText.class);
    target._mobileText = Utils.findRequiredViewAsType(source, R.id.input_mobile, "field '_mobileText'", EditText.class);
    target._passwordText = Utils.findRequiredViewAsType(source, R.id.input_password, "field '_passwordText'", EditText.class);
    target._reEnterPasswordText = Utils.findRequiredViewAsType(source, R.id.input_reEnterPassword, "field '_reEnterPasswordText'", EditText.class);
    target._signupButton = Utils.findRequiredViewAsType(source, R.id.btn_signup, "field '_signupButton'", Button.class);
    target._loginLink = Utils.findRequiredViewAsType(source, R.id.link_login, "field '_loginLink'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SignUp_Activity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._nameText = null;
    target._addressText = null;
    target._emailText = null;
    target._mobileText = null;
    target._passwordText = null;
    target._reEnterPasswordText = null;
    target._signupButton = null;
    target._loginLink = null;
  }
}
