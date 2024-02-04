package com.example.collegescheduler.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.collegescheduler.R;
import com.example.collegescheduler.item.CourseItem;
import com.example.collegescheduler.item.ExamItem;

public class CustomAdapter extends BaseAdapter {
    Context context;
    CourseItem[] courses;
    ExamItem[] exams;
    LayoutInflater inflater;

    public CustomAdapter(Context appContext, CourseItem[] courses, ExamItem[] exams) {
        this.context = appContext;
        this.courses = courses;
        this.exams = exams;
        inflater = (LayoutInflater.from(appContext));
    }

    @Override
    public int getCount() {
        return exams.length + courses.length;
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
        TextView line = view.findViewById(R.id.textViewID);
        if (i < courses.length) {
            line.setText(courses[i].toString());
        } else {
            line.setText(exams[i - courses.length].toString());
        }
        return view;
    }
}