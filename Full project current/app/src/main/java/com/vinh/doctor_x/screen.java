package com.vinh.doctor_x;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class screen extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    private FirebaseAuth fbAuth;
    int aa = 0, bb = 0;
    static int doctor;
    static int patient;

    protected boolean _active = true;
    protected int _splashTime = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        fbAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        /** Called when the activity is first created. */
        // thread for displaying the SplashScreen
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(100);
                        if (_active) {
                            waited += 100;
                        }
                    }
                    if (fbAuth.getCurrentUser() != null) {
                        check();
                        Log.d("ab", aa + "");
                    } else {
                        Intent intent = new Intent(screen.this, Login_Activity.class);
                        startActivity(intent);
                    }
                } catch (InterruptedException e) {
                    // do nothing
                } finally {

                }
            }
        };
        splashTread.start();
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        if(doctor==1){
//            Intent intent = new Intent(screen.this,Main_doctor.class);
//
//            startActivity(intent);
//        }
//        if (patient==1){
//            Intent intent = new Intent(screen.this,Main_Patient.class);
//
//            startActivity(intent);
//        }
//        else {
//            Log.d("ab", doctor + " " + patient + "");
//            Intent intent = new Intent(screen.this, Login_Activity.class);
//
//            startActivity(intent);
//        }
//    }

    public void check() {
        String key = fbAuth.getCurrentUser().getUid();
        reference.child("patient").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Intent intent = new Intent(screen.this, Main_Patient.class);
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
                    Intent intent = new Intent(screen.this, Main_doctor.class);
                    startActivity(intent);
                    doctor = 1;
                } else doctor = 0;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
