package com.vinh.doctor_x;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

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
                        //fbAuth.getCurrentUser().delete();
                        check();
                        Log.d("ab", aa + "");
                    } else {
                        Intent intent_next = new Intent(screen.this, Login_Activity.class);
                        startActivity(intent_next);
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
                    Intent intent = new Intent(screen.this, Main_Screen_Acitivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("type",1);
                    intent.putExtra("gettype",bundle);
                    startActivity(intent);

                    Log.d("ab", aa + " ");
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
                    Intent intent = new Intent(screen.this, Main_Screen_Acitivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("type",2);
                    intent.putExtra("gettype",bundle);
                    startActivity(intent);
                    Log.d("ab", aa + "patient doctor");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
