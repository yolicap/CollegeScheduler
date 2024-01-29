package com.example.collegescheduler.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.collegescheduler.R;

public class CustomAdapter extends BaseAdapter {
    Context context;
    String countryList[];
    String ratingsList[];
    LayoutInflater inflater;

    public CustomAdapter(Context appContext, String[] countryList, String[] ratingsList) {
        this.context = context;
        this.countryList = countryList;
        this.ratingsList = ratingsList;
        inflater = (LayoutInflater.from(appContext));
    }

    @Override
    public int getCount() {
        return countryList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_list_view_test, null);
        TextView line = (TextView) view.findViewById(R.id.textViewID);
        line.setText(countryList[i] + " is rated " + ratingsList[i]);
        return view;
    }
}
