package com.example.collegescheduler.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.collegescheduler.DataBase;
import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentHomeBinding;
import com.example.collegescheduler.item.CourseItem;
import com.example.collegescheduler.item.ExamItem;

import java.util.Enumeration;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    ListView simpleList;

    int courseSize = DataBase.getCourseDict().size();
    int examSize = DataBase.getExamDict().size();
    CourseItem[] courses = new CourseItem[courseSize];
    ExamItem[] exams = new ExamItem[examSize];

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // create binding
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // populate courses[] and exams[]
        Enumeration<CourseItem> courseEnu = DataBase.getCourseDict().elements();
        int ind = 0;
        while (courseEnu.hasMoreElements()) {
            courses[ind] = courseEnu.nextElement();
            ind++;
        }
        Enumeration<ExamItem> examEnu = DataBase.getExamDict().elements();
        ind = 0;
        while (examEnu.hasMoreElements()) {
            exams[ind] = examEnu.nextElement();
        }

        // handle ListView
        simpleList = root.findViewById(R.id.list_view);
        CustomAdapter customAdapter = new CustomAdapter(root.getContext(), courses, exams);
        simpleList.setAdapter(customAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}