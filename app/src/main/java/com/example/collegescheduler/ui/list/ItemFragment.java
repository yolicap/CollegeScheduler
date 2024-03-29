package com.example.collegescheduler.ui.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.collegescheduler.DataBase;
import com.example.collegescheduler.R;
import com.example.collegescheduler.item.AbstractItem;
import com.example.collegescheduler.ui.list.item.ItemContent;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ItemFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";

    // TODO : find better solution
    public static Spinner assignmentSpinner = null;

    private final ItemListAdapter itemListAdapter =
            new ItemListAdapter();
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    @SuppressWarnings("unused")
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        Context context = view.getContext();

        /* RecyclerView Setup */
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        recyclerView.setAdapter(itemListAdapter);
//        updateList(ItemContent.ITEMS);

        /* AssignmentSortSpinner */
        Spinner assignmentSortSpinner = (Spinner) view.findViewById(R.id.item_sort_by_spinner);
        assignmentSpinner = assignmentSortSpinner;
        assignmentSortSpinner.setOnItemSelectedListener(new AssignmentSortSelectedListener(itemListAdapter));

        // Create an ArrayAdapter using the string array and a default itemTypeSpinner layout.
        ArrayAdapter<CharSequence> assignmentSortAdapter = ArrayAdapter.createFromResource(
                context,
                R.array.assignment_sort_types,
                android.R.layout.simple_spinner_item
                // TODO :
        );
        // Specify the layout to use when the list of choices appears.
        assignmentSortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the itemTypeSpinnerAdapter to the itemTypeSpinner.
        assignmentSortSpinner.setAdapter(assignmentSortAdapter);

        /* Item Type Spinner */
        Spinner itemTypeSpinner = (Spinner) view.findViewById(R.id.item_type_spinner);
        // TODO : (refactor) extract class instead of extending this one
        itemTypeSpinner.setOnItemSelectedListener(new ItemTypeSelectedListener(itemListAdapter));

        // Create an ArrayAdapter using the string array and a default itemTypeSpinner layout.
        ArrayAdapter<CharSequence> itemTypeSpinnerAdapter = ArrayAdapter.createFromResource(
                context,
                R.array.item_types,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        itemTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the itemTypeSpinnerAdapter to the itemTypeSpinner.
        itemTypeSpinner.setAdapter(itemTypeSpinnerAdapter);

        return view;
    }

}

