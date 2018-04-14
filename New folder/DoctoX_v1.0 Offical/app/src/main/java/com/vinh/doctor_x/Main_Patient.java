package com.vinh.doctor_x;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Vinh on 10-Apr-18.
 */

public class Main_Patient extends AppCompatActivity {
    ImageButton ss ;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frg_main_patient);
        ss = findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();
        ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(Main_Patient.this,Login_Activity.class);
                startActivity(intent);
            }
        });

    }
}
