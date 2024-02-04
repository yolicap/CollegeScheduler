package com.example.collegescheduler.ui.list;

import android.content.Context;
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
public class ItemFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private static final String ARG_COLUMN_COUNT = "column-count";
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

        /* Spinner */
        Spinner spinner = (Spinner) view.findViewById(R.id.item_type_spinner);
        // TODO : (refactor) extract class instead of extending this one
        spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                context,
                R.array.item_types,
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
        switch( position ) {
            case 0: ;
                updateList(DataBase.getExamsList());
                break;
            case 1 :
                updateList(DataBase.getCoursesList());
                break;
            case 2 :
                updateList(DataBase.getAssignmentsList());
                break;
            case 3 :
            default:
                // TODO : set to all
                updateList(new ArrayList<AbstractItem>());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO : use global items list
        updateList(new ArrayList<AbstractItem>());
    }

    private void updateList(List<? extends AbstractItem> newList){
        ArrayList<AbstractItem> list = new ArrayList<AbstractItem>(newList);
        for (AbstractItem o : list){
            System.out.println("hello!");
            System.out.println(o.getName());
        }
        itemListAdapter.submitList(list);
    }
}