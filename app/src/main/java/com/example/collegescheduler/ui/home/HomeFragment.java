package com.example.collegescheduler.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.DataBase;
import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentHomeBinding;
import com.example.collegescheduler.item.AbstractItem;
import com.example.collegescheduler.item.AssignmentItem;
import com.example.collegescheduler.item.CourseItem;
import com.example.collegescheduler.item.ExamItem;
import com.example.collegescheduler.ui.list.ItemFragment;
import com.example.collegescheduler.ui.list.ItemListAdapter;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private static final String ARG_COLUMN_COUNT = "column-count";

    private final HomeListAdapter customAdapter =
            new HomeListAdapter();

    private int mColumnCount = 1;

    public HomeFragment() {
    }

    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Context context = view.getContext();

        /* RecyclerView Setup */
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.homeList);
        recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        recyclerView.setAdapter(customAdapter);
//        updateList(ItemContent.ITEMS);

        /* Spinner */
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner_home);
        // TODO : (refactor) extract class instead of extending this one
        spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                context,
                R.array.home_item_types,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        spinner.setAdapter(adapter);
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // use position to show items in list
        // TODO : create ViewUpdater to update views when global lists are updated
        // TODO : create enum in item package
        List<AbstractItem> newList = new ArrayList<AbstractItem>();
        newList.addAll(DataBase.getAssignmentsList());
        newList.addAll(DataBase.getExamsList());
        switch (position) {
            case 2:
                updateList(DataBase.getExamsList());
                break;
            case 1:
                updateList(DataBase.getAssignmentsList());
                break;
            case 0:
            default:
                updateList(newList);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO : use global items list
        updateList(new ArrayList<AbstractItem>());
    }

    private void updateList(List<? extends AbstractItem> newList){
        ArrayList<AbstractItem> list = new ArrayList<AbstractItem>(newList);
        list.forEach(item -> System.out.println(item.getName()));
        customAdapter.submitList(list);
    }
}

/*
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    ListView simpleList;

    int courseSize;
    int examSize;
    CourseItem[] courses;
    ExamItem[] exams;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // create binding
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        courseSize = DataBase.getCourseDict().size();
        examSize = DataBase.getExamDict().size();
        courses = new CourseItem[courseSize];
        exams = new ExamItem[examSize];

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
*/