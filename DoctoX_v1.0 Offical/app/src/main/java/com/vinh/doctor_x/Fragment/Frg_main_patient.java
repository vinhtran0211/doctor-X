package com.vinh.doctor_x.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
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
import com.vinh.doctor_x.R;

/**
 * Created by nntd290897 on 3/17/18.
 */



public class Frg_main_patient extends Fragment {
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

        btn_bookappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Clicked","clicked");
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();


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

}
