package com.vinh.doctor_x.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vinh.doctor_x.Main_Screen_Acitivity;
import com.vinh.doctor_x.R;
import com.vinh.doctor_x.Realtime_Location_Map_Activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nntd290897 on 5/19/18.
 */

public class Frg_emer_book_patient extends Fragment {

    private View view;
    private EditText txt_location_emer;
    private Button btn_search_emer;
    private FragmentManager mFragmentManager;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    private Dialog dialog;

    private static String key_patient_request_zone_emer;

    public static String getKey_patient_request_zone_emer() {
        return key_patient_request_zone_emer;
    }

    public static void key_patient_request_zone_emer(String key_patient_request_zone) {
        Frg_emer_book_patient.key_patient_request_zone_emer = key_patient_request_zone;
    }

    private FirebaseDatabase database= FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_DarkHAB);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        view = inflater.inflate(R.layout.frg_emer_book_patient,container,false);
        txt_location_emer = (EditText)view.findViewById(R.id.txt_chooselocation_emer);
        btn_search_emer = (Button)view.findViewById(R.id.btn_search_emer);
        dialog = new Dialog(getContext());
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
                mFragmentManager = getActivity().getSupportFragmentManager();
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
        btn_search_emer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                System.out.println("Current time => "+c.getTime());

                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                String key = df.format(c.getTime());
                key_patient_request_zone_emer(key);
                myRef.child("request_zone").child(Main_Screen_Acitivity.getPatient().getPhone()).child(key).child("Address").setValue(txt_location_emer.getText().toString());
                myRef.child("request_zone").child(Main_Screen_Acitivity.getPatient().getPhone()).child(key).child("Doctor").setValue("null");
                myRef.child("request_zone").child(Main_Screen_Acitivity.getPatient().getPhone()).child(key).child("Time").setValue(key);
                myRef.child("request_zone").child(Main_Screen_Acitivity.getPatient().getPhone()).child(key).child("Lat").setValue(Main_Screen_Acitivity.getPicker_Lat());
                myRef.child("request_zone").child(Main_Screen_Acitivity.getPatient().getPhone()).child(key).child("Log").setValue(Main_Screen_Acitivity.getPicker_Log());
                myRef.child("request_zone").child(Main_Screen_Acitivity.getPatient().getPhone()).child(key).child("WhoCome").setValue("Doctor");
                myRef.child("request_zone").child(Main_Screen_Acitivity.getPatient().getPhone()).child(key).child("Type").setValue("Appointment Normal");
                Intent i = new Intent(getActivity(), Realtime_Location_Map_Activity.class);
                i.putExtra("type_appoitment", "emer");
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
            }
        });
        return view;
    }

    public void onResume(){
        super.onResume();

        String item = ((Main_Screen_Acitivity)getActivity()).getLocationpicker();

        txt_location_emer.setText(item);
        //if item != null do some crazy shizzle
        //set MainActivity searchitem to null to avoid reloading it
    }
}
