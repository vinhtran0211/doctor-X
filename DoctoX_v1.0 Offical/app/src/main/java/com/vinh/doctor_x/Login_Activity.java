package com.vinh.doctor_x;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vinh.doctor_x.User.Doctor_class;
import com.vinh.doctor_x.User.Location_cr;
import com.vinh.doctor_x.User.Patient_class;
import com.vinh.doctor_x.inputpin;

import java.util.concurrent.TimeUnit;

public class Login_Activity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    Button login;
    Button logindialog;
    inputpin txt_pin_entry;
    private Dialog dialog;
    private EditText phoneNumber;
    FirebaseDatabase database;
    DatabaseReference reference;
    boolean checkdata = false; // kiểm tra xem có dữ liệu hay
    String b,a ;
    String c = "1";


    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    private FirebaseAuth fbAuth;
    private ProgressDialog progress;


    public static Patient_class patient = new Patient_class();

    public static Doctor_class doctor = new Doctor_class();

    public static String key;

    public static Patient_class getPatient() {
        return patient;
    }

    public void setPatient(Patient_class patient) {
        this.patient = patient;
    }

    public static Doctor_class getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor_class doctor) {
        this.doctor = doctor;
    }

    public static String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        login= (Button) findViewById(R.id.btn_login);
        phoneNumber = (EditText)findViewById(R.id.input_phone) ;

        dialog = new Dialog(Login_Activity.this);
        dialog.setContentView(R.layout.dialog_getverifi);

        logindialog =(Button)dialog.findViewById(R.id.btn_login1) ;
        txt_pin_entry = (inputpin)dialog.findViewById(R.id.txt_pin_entry) ;
        fbAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        progress=new ProgressDialog(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCode();
                dialog.show();

            }
        });
        logindialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logindialog.setEnabled(false);
                verifyCode(v);
            }
        });
    };
    public void sendCode() {

        String locale = this.getResources().getConfiguration().locale.getCountry();
        Toast.makeText(this,locale+"",Toast.LENGTH_LONG).show();

        getDialingCode getDialingCode = new getDialingCode();
        String phone = "+" + getDialingCode.compareDialingCode(removeFirstChar(phoneNumber.getText().toString())) + removeFirstChar(phoneNumber.getText().toString());

        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                verificationCallbacks);
    }

    public String removeFirstChar(String s){
        return s.substring(1);
    }


    private void setUpVerificatonCallbacks() {

        verificationCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(
                            PhoneAuthCredential credential) {
                        signInWithPhoneAuthCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                            Log.d(TAG, "Invalid credential: "
                                    + e.getLocalizedMessage());
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            // SMS quota exceeded
                            Log.d(TAG, "SMS Quota exceeded.");
                        }
                    }

                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken token) {

                        phoneVerificationId = verificationId;
                        resendToken = token;
                    }
                };
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            if(check() == false){
                                Intent intent = new Intent(Login_Activity.this, SignUp_Activity.class);
                                startActivity(intent);
                            }
                        } else {
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
    public void verifyCode(View view) {
        String code = txt_pin_entry.getText().toString();

        if(code.isEmpty())
        {
            Effectstype effect;
            NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(Login_Activity.this);
            effect = Effectstype.Shake;
            dialogBuilder
                    .withTitle("Waiting .....")                                  //.withTitle(null)  no title
                    .withTitleColor("#FFFFFF")                                  //def
                    .withDividerColor("#11000000")                              //def
                    .withMessage("Please you must receive passcode in next time")                     //.withMessage(null)  no Msg
                    .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                    .withDialogColor("#FFE74C3C")                               //def  | withDialogColor(int resid)                               //def
                    //.withIcon(getResources().getDrawable(R.drawable.doctor))
                    .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                    .withDuration(700)                                          //def
                    .withEffect(effect)                                         //def Effectstype.Slidetop
                    .withButton1Text("OK I know!")                                      //def gone
                    //time.withButton2Text("Cancel")                                  //def gone
                    //.setCustomView(R.layout.custom_view,v.getContext())         //.setCustomView(View or ResId,context)
                    .setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogBuilder.dismiss();
                        }
                   /* .setButton2Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(v.getContext(), "i'm btn2", Toast.LENGTH_SHORT).show();
                        }*/
                    })
                    .show();
        }
        else{
            Log.d("d",code);
            PhoneAuthCredential credential =
                    PhoneAuthProvider.getCredential(phoneVerificationId, code);
            signInWithPhoneAuthCredential(credential);

            Log.i("logindialog","clicked");
        }

    }
    public Boolean check()
    {
        String key =  fbAuth.getCurrentUser().getUid();
        reference.child("patient").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    setPatient(dataSnapshot.getValue(Patient_class.class));
                    setKey(dataSnapshot.getKey());
                    Toast.makeText(Login_Activity.this, screen.getPatient().getPhone(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login_Activity.this, Main_Screen_Acitivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("type",1);
                    intent.putExtra("gettype",bundle);
                    startActivity(intent);
                    checkdata = true;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        if(checkdata == true)
        {
            return true;
        }
        else {
            reference.child("doctor").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        setDoctor(dataSnapshot.getValue(Doctor_class.class));
                        setKey(dataSnapshot.getKey());
                        Intent intent = new Intent(Login_Activity.this,Main_Screen_Acitivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("type",2);
                        intent.putExtra("gettype",bundle);
                        startActivity(intent);
                        checkdata = true;
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            if(checkdata == true)
            {
                return true;
            }
        }
        return false;
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
