package com.example.collegescheduler.ui.list;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.collegescheduler.DataBase;
import com.example.collegescheduler.R;
import com.example.collegescheduler.item.AbstractItem;

import java.util.ArrayList;
import java.util.List;

public class ItemTypeSelectedListener implements AdapterView.OnItemSelectedListener {
    private final ItemListAdapter itemListAdapter;

    public ItemTypeSelectedListener(ItemListAdapter itemListAdapter) {
        super();
        this.itemListAdapter = itemListAdapter;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // use position to show items in list
        // TODO : create ViewUpdater to update views when global lists are updated
        // TODO : create enum in item package
        Spinner assignmentSortSpinner = ItemFragment.assignmentSpinner;
        if (assignmentSortSpinner == null){
            System.out.println("Couldn't find assignmentSortSpinner :(");
            return ;
        }
        switch (position) {
            /* EXAM */
            case 2:
                assignmentSortSpinner.setVisibility(View.INVISIBLE);
                updateList(DataBase.getExamsList());
                break;

            /* ASSIGNMENT */
            case 1:
                assignmentSortSpinner.setVisibility(View.VISIBLE);
                updateList(DataBase.getAssignmentsList());
                break;

            /* COURSE (default selection) */
            case 0:
            default:
                assignmentSortSpinner.setVisibility(View.INVISIBLE);
                updateList(DataBase.getCoursesList());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO : use global items list
        updateList(new ArrayList<AbstractItem>());
    }

    private void updateList(List<? extends AbstractItem> newList) {
        ArrayList<AbstractItem> list = new ArrayList<AbstractItem>(newList);
        list.forEach(item -> System.out.println(item.getName()));
        itemListAdapter.submitList(list);
    }

}
