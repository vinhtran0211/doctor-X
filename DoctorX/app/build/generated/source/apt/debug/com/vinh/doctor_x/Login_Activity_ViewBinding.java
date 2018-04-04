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

public class Login_Activity_ViewBinding implements Unbinder {
  private Login_Activity target;

  @UiThread
  public Login_Activity_ViewBinding(Login_Activity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Login_Activity_ViewBinding(Login_Activity target, View source) {
    this.target = target;

    target._emailText = Utils.findRequiredViewAsType(source, R.id.input_email, "field '_emailText'", EditText.class);
    target._passwordText = Utils.findRequiredViewAsType(source, R.id.input_password, "field '_passwordText'", EditText.class);
    target._loginButton = Utils.findRequiredViewAsType(source, R.id.btn_login, "field '_loginButton'", Button.class);
    target._signupLink = Utils.findRequiredViewAsType(source, R.id.link_signup, "field '_signupLink'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Login_Activity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._emailText = null;
    target._passwordText = null;
    target._loginButton = null;
    target._signupLink = null;
  }
}
