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

import com.vinh.doctor_x.Login_Activity;
import com.vinh.doctor_x.R;

/**
 * Created by nntd290897 on 3/17/18.
 */



public class Frg_main_doctor extends Fragment {
    private View view;
    private ImageButton btn_appointment , btn_mypatient,btn_history,btn_healthytips,btn_settingprofile,btn_logout;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frg_main_doctor,container,false);
        btn_logout = (ImageButton)view.findViewById(R.id.btn_logout_md);


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Logout Pressed",Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getActivity(), Login_Activity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);

            }
        });
        return view;
    }

}
