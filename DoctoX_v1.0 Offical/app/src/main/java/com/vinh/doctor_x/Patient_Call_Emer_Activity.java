package com.vinh.doctor_x;

import android.app.Dialog;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vinh.doctor_x.Fragment.Frg_Map;

public class Patient_Call_Emer_Activity extends AppCompatActivity {
    private EditText txt_location_emer;
    private Button btn_search_emer;
    private FragmentManager mFragmentManager;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_call_emer_layout);
        getSupportActionBar().setTitle("History"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar

        txt_location_emer = (EditText)findViewById(R.id.txt_chooselocation_emer);
        btn_search_emer = (Button)findViewById(R.id.btn_search_emer);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_chooselocation);
        dialog.setTitle("Choose Yours Location");


        Button btn_getdefault = (Button) dialog.findViewById(R.id.btn_getdefault);
        Button btn_getcurrently = (Button) dialog.findViewById(R.id.btn_getcurrently);

        btn_getdefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_location_emer.setText(Main_Screen_Acitivity.getPatient().getAddress());
                dialog.dismiss();
            }
        });

        btn_getcurrently.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                mFragmentManager = getSupportFragmentManager();
                transaction = mFragmentManager.beginTransaction();
                Frg_Map map = new Frg_Map();
                transaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
                transaction.replace(R.id.frg_patient_main, map);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        txt_location_emer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item1) {
        // TODO Auto-generated method stub
        int id = item1.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item1);
    }
}
