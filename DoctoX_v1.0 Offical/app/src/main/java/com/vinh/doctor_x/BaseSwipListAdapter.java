package com.vinh.doctor_x;

import android.widget.BaseAdapter;

/**
 * Created by nntd290897 on 5/18/18.
 */
public abstract class BaseSwipListAdapter extends BaseAdapter {

    public boolean getSwipEnableByPosition(int position){
        return true;
    }



}