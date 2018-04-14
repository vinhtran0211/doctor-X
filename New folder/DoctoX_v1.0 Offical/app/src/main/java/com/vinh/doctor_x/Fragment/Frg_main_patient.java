package com.vinh.doctor_x.Fragment;

import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vinh.doctor_x.R;

/**
 * Created by nntd290897 on 3/17/18.
 */



public class Frg_main_patient extends Fragment {
    private View view;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.frg_main_patient,container,false);


        return view;
    }

}
