package com.vinh.doctor_x.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.vinh.doctor_x.Doctor_Follow_Activity;
import com.vinh.doctor_x.Doctor_History_Acitivity;
import com.vinh.doctor_x.Doctor_Realtime_Map_Activity;
import com.vinh.doctor_x.Login_Activity;
import com.vinh.doctor_x.R;

/**
 * Created by nntd290897 on 3/17/18.
 */



public class Frg_main_doctor extends Fragment {
    private View view;
    private ImageButton btn_appointment , btn_mypatient,btn_history,btn_healthytips,btn_settingprofile,btn_logout;
    private FirebaseAuth mAuth;
    private  FragmentManager fragmentManager ;
    private FragmentTransaction fragmentTransaction ;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frg_main_doctor,container,false);
        btn_logout = (ImageButton)view.findViewById(R.id.btn_logout_md);
        mAuth = FirebaseAuth.getInstance();
        btn_appointment = (ImageButton)view.findViewById(R.id.btn_getappointment);
        btn_mypatient = (ImageButton)view.findViewById(R.id.btn_patientofdoctor);
        btn_history = (ImageButton)view.findViewById(R.id.btn_historyofdoctor);
        btn_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fragmentManager = getActivity().getSupportFragmentManager();
                //fragmentTransaction = fragmentManager.beginTransaction();


                Intent i = new Intent(getActivity(),Doctor_Realtime_Map_Activity.class);
                i.putExtra("type","showlist");
                startActivity(i);
            }
        });

        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),Doctor_History_Acitivity.class);
                startActivity(i);
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

        btn_mypatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),Doctor_Follow_Activity.class);
                startActivity(i);
            }
        });

        return view;


    }

}
