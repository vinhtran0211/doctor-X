package com.vinh.doctor_x.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vinh.doctor_x.R;

/**
 * Created by nntd290897 on 3/18/18.
 */

public class Frg_bookappointment extends Fragment {
    View view;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_DarkHAB);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.frg_bookappointment, container, false);

       // return view;
    }


}
