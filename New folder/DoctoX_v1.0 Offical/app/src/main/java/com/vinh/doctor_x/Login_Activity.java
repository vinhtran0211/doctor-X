package com.vinh.doctor_x;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import com.vinh.doctor_x.test.inputpin;

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
    int check_1 = 0,check_2 = 0;
    boolean aa = false;
    String b,a ;
    String c = "1";



    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    private FirebaseAuth fbAuth;
    private ProgressDialog progress;



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

                    verifyCode(v);






                }
            });




    }


        ;



    public void sendCode() {


        String phone = phoneNumber.getText().toString();

        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                verificationCallbacks);
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
                                check();
                                if(aa == false){
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
        Log.d("d",code);
        PhoneAuthCredential credential =
                PhoneAuthProvider.getCredential(phoneVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }
    public Boolean check()
    {
        String key =  fbAuth.getCurrentUser().getUid();



        reference.child("patient").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Intent intent = new Intent(Login_Activity.this, Main_Patient.class);
                    startActivity(intent);
                    aa = true;
                }
                else {

                    Intent intent = new Intent(Login_Activity.this, SignUp_Activity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        reference.child("doctor").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Intent intent = new Intent(Login_Activity.this,Main_doctor.class);
                    startActivity(intent);
                    aa = true;

                }else {

                    Intent intent = new Intent(Login_Activity.this, SignUp_Activity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        return aa;
    }




}
