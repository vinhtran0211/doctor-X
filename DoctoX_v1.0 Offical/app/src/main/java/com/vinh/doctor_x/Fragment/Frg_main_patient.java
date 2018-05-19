package com.vinh.doctor_x.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.vinh.doctor_x.Login_Activity;
import com.vinh.doctor_x.Main_Screen_Acitivity;
import com.vinh.doctor_x.Medical.MedicalProblems;
import com.vinh.doctor_x.NearbyLocations.GMap.ListHealthCenters;
import com.vinh.doctor_x.R;

/**
 * Created by nntd290897 on 3/17/18.
 */



public class Frg_main_patient extends Fragment {

    public static ProgressDialog progressDialog;
    private View view;
    private ImageButton btn_bookappointment, btn_bookemer, btn_findhospital,btn_mydoctor,btn_healthytips, btn_reminder,btn_logout;
    private FirebaseAuth mAuth;
    private  FragmentManager fragmentManager ;
    private FragmentTransaction fragmentTransaction ;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mAuth = FirebaseAuth.getInstance();
        view = inflater.inflate(R.layout.frg_main_patient,container,false);
        btn_bookappointment = (ImageButton)view.findViewById(R.id.btn_bookappointment);
        btn_bookemer = (ImageButton)view.findViewById(R.id.btn_emergencey_mp);
        btn_findhospital = (ImageButton)view.findViewById(R.id.btn_findhospital_mp);
        btn_mydoctor = (ImageButton)view.findViewById(R.id.btn_mydoctor_mp);
        btn_healthytips = (ImageButton)view.findViewById(R.id.btn_healthytips_mp);
        btn_reminder = (ImageButton)view.findViewById(R.id.btn_reminder_mp);
        btn_logout = (ImageButton)view.findViewById(R.id.btn_logout_mp);


        btn_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicalProblems();
            }
        });

        btn_findhospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hospitalLocations();
            }
        });
        btn_bookappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Clicked","clicked");
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();

                Main_Screen_Acitivity.setCheckBtnSearch(true);
                Frg_bookappointment fragment = new Frg_bookappointment();
                fragmentTransaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
                fragmentTransaction.replace(R.id.frg_patient_main, fragment);
                //fragmentTransaction.replace(R.id.frg_patient_main, map);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btn_bookemer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();


                Main_Screen_Acitivity.setCheckBtnSearch(true);
                Frg_emer_book_patient fragment = new Frg_emer_book_patient();
                fragmentTransaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
                fragmentTransaction.replace(R.id.frg_patient_main, fragment);
                //fragmentTransaction.replace(R.id.frg_patient_main, map);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Logout Pressed",Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent i = new Intent(getActivity(), Login_Activity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);

            }
        });

        return view;
    }

    void medicalProblems() {
        loading("Loading...");
        Intent intent = new Intent(getActivity(), MedicalProblems.class);
        startActivity(intent);
    }

    void hospitalLocations() {
        if(isNetworkAvailable()) {
            loading("Scanning Location...");
            Intent intent = new Intent(getActivity(), ListHealthCenters.class);
            startActivity(intent);
        }else
            Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_LONG).show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            connectivityManager = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    void loading(String message){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

}
