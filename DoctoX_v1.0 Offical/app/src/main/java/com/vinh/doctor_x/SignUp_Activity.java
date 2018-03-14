package com.vinh.doctor_x;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vinh.doctor_x.Fragment.Frg_fillinfro_doctor;
import com.vinh.doctor_x.Fragment.Frg_fillinfro_patient;
import com.vinh.doctor_x.User.Patient_class;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUp_Activity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";


    private ProgressDialog message;
    FirebaseDatabase database;
    DatabaseReference reference;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseauthlistener;


    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_address) EditText _addressText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_mobile) EditText _mobileText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;


    RadioButton rdb_cpatient ;
    RadioButton rdb_cdoctor ;
    RadioGroup rdg_chooseType ;
    FragmentManager manager;
    FragmentTransaction transaction;

    Frg_fillinfro_patient frg_patient = new Frg_fillinfro_patient();
    Frg_fillinfro_doctor frg_doctor = new Frg_fillinfro_doctor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);

        contextOfApplication = getApplicationContext();

        rdg_chooseType = (RadioGroup)findViewById(R.id.rdg_chooseType);
        rdb_cpatient = (RadioButton)findViewById(R.id.rdb_cpatient);
        rdb_cdoctor = (RadioButton)findViewById(R.id.rdb_cdoctor);


        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();


        firebaseauthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null)
                {
                    Intent intent = new Intent(SignUp_Activity.this,Login_Activity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),Login_Activity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        rdg_chooseType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                manager= getSupportFragmentManager();
                transaction= manager.beginTransaction();
                if(rdb_cdoctor.isChecked())
                {
                    transaction.replace(R.id.frg_frisetup, frg_doctor, "Fragment_Fill_Doctor");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else if(rdb_cpatient.isChecked())
                {
                    transaction.replace(R.id.frg_frisetup, frg_patient, "Fragment_Fill_Patient");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    public void signup() {
        // Log.d(TAG, "Signup");

        /*if (!validate()) {
            onSignupFailed();
            return;
        }*/

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUp_Activity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        final String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        // TODO: Implement your own signup logic here.

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       Log.d(task.toString(),"taskcheck");
                        if (task.isSuccessful()) {

                            Patient_class v = new Patient_class();
                            String s[] = email.split("@");
                            v.setName(s[0]);
                            v.setEmail(email);
                            v.setPhone("none");
                            v.setGender(1);
                            v.setBirthday("none");
                            v.setMedicalFiles("none");
                            v.setStatus("none");
                            v.setAvatar(1);

                            reference.child("waiting").child(s[0]).setValue(v);


                            Toast.makeText(SignUp_Activity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUp_Activity.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
       // setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Sign Up  failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();



        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (address.isEmpty()) {
            _addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            _addressText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseauthlistener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.addAuthStateListener(firebaseauthlistener);
    }
}
