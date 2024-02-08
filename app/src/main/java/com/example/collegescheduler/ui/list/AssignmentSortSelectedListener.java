package com.example.collegescheduler.ui.list;

import android.view.View;
import android.widget.AdapterView;

import com.example.collegescheduler.DataBase;
import com.example.collegescheduler.item.AbstractItem;
import com.example.collegescheduler.item.AssignmentItem;

import java.util.ArrayList;
import java.util.List;

public class AssignmentSortSelectedListener implements AdapterView.OnItemSelectedListener {
    private final ItemListAdapter itemListAdapter;

    public AssignmentSortSelectedListener (ItemListAdapter itemListAdapter) {
        super();
        this.itemListAdapter = itemListAdapter;
    }

    // TODO : super bad logic but this is due tonight
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // use position to show items in list
        // TODO : create ViewUpdater to update views when global lists are updated
        // TODO : create enum in item package
        switch (position) {
            /* COURSE */
            case 1:
                updateList(DataBase.getAssignmentListSorted(new AssignmentItem.CourseNameComparator()));
                break;
            /* BY DUE DATE */
            case 0:
                updateList(DataBase.getAssignmentListSorted(new AssignmentItem.DueDateComparator()));
                break;
        }
    }

    // TODO : needs to be extracted
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    private void updateList(List<? extends AbstractItem> newList) {
        ArrayList<AbstractItem> list = new ArrayList<AbstractItem>(newList);
        list.forEach(item -> System.out.println(item.getName()));
        itemListAdapter.submitList(list);
    }
}
