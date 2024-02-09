package com.example.collegescheduler.ui.home;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.example.collegescheduler.item.ItemType;
import com.example.collegescheduler.ui.list.ItemFragment;
import com.example.collegescheduler.ui.list.ItemListAdapter;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private static final String ARG_COLUMN_COUNT = "column-count";

    private final HomeListAdapter homeListAdapter =
            new HomeListAdapter();

    private boolean filterTodoOnly = false;
    private ItemType itemShowing = ItemType.EXAM;

    private int mColumnCount = 1;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(int columnCount) {
        HomeFragment fragment = new HomeFragment();
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
        RecyclerView recyclerView = view.findViewById(R.id.homeList);
        recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        recyclerView.setAdapter(homeListAdapter);
//        updateList(ItemContent.ITEMS);

        /* Spinner */
        Spinner spinner = view.findViewById(R.id.spinner_home);
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

        /* Filter Button */
        Button filterButton = view.findViewById(R.id.filter_button);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update button text and flag
                filterButton.setText(filterTodoOnly ? "SHOW ALL" : "TODO ONLY");
                filterTodoOnly = !filterTodoOnly;


                // TODO : "nobody likes just writing if statements everywhere but someone has to do it"

                // fetch new list
                if (itemShowing == ItemType.EXAM) updateToExam();
                else if (itemShowing == ItemType.ASSIGNMENT) updateToAssignment();

            }
        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // use position to show items in list
        // TODO : create ViewUpdater to update views when global lists are updated
        // TODO : create enum in item package
        switch (position) {
            /* ASSIGNMENT */
            case 0: updateToAssignment();
                break;
            /* EXAM (Default) */
            case 1:
            default: updateToExam();
        }
    }

    private void updateToAssignment(){
        if (filterTodoOnly) {
            updateList(DataBase.getAssignmentListTodo());
        } else {
            updateList(DataBase.getAssignmentsList());
        }
        itemShowing = ItemType.ASSIGNMENT;
    }

    private void updateToExam(){
        if (filterTodoOnly) {
            updateList(DataBase.getExamListTodo());
        } else {
            updateList(DataBase.getExamsList());
        }
        itemShowing = ItemType.EXAM;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO : use global items list
        updateList(new ArrayList<AbstractItem>());
    }

    private void updateList(List<? extends AbstractItem> newList){
        ArrayList<AbstractItem> list = new ArrayList<AbstractItem>(newList);
        list.forEach(item -> System.out.println(item.getName()));
        homeListAdapter.submitList(list);
    }
}