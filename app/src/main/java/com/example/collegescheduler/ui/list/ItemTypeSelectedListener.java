package com.example.collegescheduler.ui.list;

import android.view.View;
import android.widget.AdapterView;

import com.example.collegescheduler.DataBase;
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
        switch (position) {
            case 2:
                updateList(DataBase.getExamsList());
                break;
            case 1:
                updateList(DataBase.getAssignmentsList());
                break;
            case 0:
            default:
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
