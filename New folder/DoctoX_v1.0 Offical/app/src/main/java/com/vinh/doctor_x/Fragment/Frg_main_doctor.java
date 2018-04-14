package com.vinh.doctor_x.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vinh.doctor_x.R;

/**
 * Created by Vinh on 10-Apr-18.
 */

public class Frg_main_doctor {
    private View view;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.frg_main_doctor,container,false);


        return view;
    }
}
