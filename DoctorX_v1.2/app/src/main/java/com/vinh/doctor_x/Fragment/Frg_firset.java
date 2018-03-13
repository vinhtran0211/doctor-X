package com.vinh.doctor_x.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vinh.doctor_x.R;

/**
 * Created by nntd290897 on 3/8/18.
 */

public class Frg_firset extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_typeuser,container,false);

        return view;
    }
}

