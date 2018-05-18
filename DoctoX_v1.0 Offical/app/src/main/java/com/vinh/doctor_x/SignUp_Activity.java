package com.vinh.doctor_x;


import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vinh.doctor_x.Fragment.Frg_fillinfro_doctor;
import com.vinh.doctor_x.Fragment.Frg_fillinfro_patient;
import com.vinh.doctor_x.User.Location_cr;

public class SignUp_Activity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    public Location_cr location_cr = new Location_cr();


    RadioButton rdb_cpatient ;
    RadioButton rdb_cdoctor ;
    RadioGroup rdg_chooseType ;
    FragmentManager manager;
    FragmentTransaction transaction;
    FirebaseDatabase database;
    DatabaseReference reference;
    int check_1 = 0,check_2 = 0;


    private FirebaseAuth mAuth;

    Frg_fillinfro_patient frg_patient = new Frg_fillinfro_patient();
    Frg_fillinfro_doctor frg_doctor = new Frg_fillinfro_doctor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        mAuth = FirebaseAuth.getInstance();
//        String key =  mAuth.getCurrentUser().getUid();
        contextOfApplication = getApplicationContext();
        rdg_chooseType = (RadioGroup)findViewById(R.id.rdg_chooseType);
        rdb_cpatient = (RadioButton)findViewById(R.id.rdb_cpatient);
        rdb_cdoctor = (RadioButton)findViewById(R.id.rdb_cdoctor);

        rdb_cdoctor.setChecked(false);
        rdb_cpatient.setChecked(false);
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
