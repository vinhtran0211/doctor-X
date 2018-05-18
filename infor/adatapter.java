package com.vinh.doctor_x;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.vinh.doctor_x.User.Comment;

import java.util.ArrayList;


/**
 * Created by Vinh on 28-Apr-18.
 */

public class adatapter extends BaseAdapter {
    Context context;
    ArrayList<Comment> ar;
    private LayoutInflater inflater;

    public adatapter(Context _context, ArrayList<Comment> _items) {
        inflater = LayoutInflater.from(_context);
        this.ar = _items;
        this.context = _context;

    }


    @Override
    public int getCount() {
        return ar.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comment chuDe = ar.get(position);
        View view = convertView;


        if (view == null)
            view = inflater.inflate(R.layout.custom_comment, null);

        TextView text = (TextView) view.findViewById(R.id.text);
        TextView name = (TextView) view.findViewById(R.id.name);
        RatingBar rating = (RatingBar) view.findViewById(R.id.rating);

        text.setText(chuDe.getText());
        name.setText(chuDe.getTen());
        rating.setRating(chuDe.getRating());



        return view;

    }
}