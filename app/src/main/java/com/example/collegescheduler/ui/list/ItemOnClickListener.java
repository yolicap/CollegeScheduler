package com.example.collegescheduler.ui.list;

import android.os.Bundle;
import android.view.View;

import androidx.navigation.Navigation;

import com.example.collegescheduler.R;
import com.example.collegescheduler.item.AbstractItem;

public class ItemOnClickListener implements View.OnClickListener {
    private final AbstractItem item;

    public ItemOnClickListener(AbstractItem item){
        super();
        this.item = item;
    }
    @Override
    public void onClick(View v) {
        System.out.println("Item clicked!");
        Bundle argBundle = new Bundle();
        argBundle.putString("item_uuid", this.item.getId().toString());
        System.out.println("id : " + this.item.getId().toString());

        // View needs to be that of the id/navigation_list
        // https://developer.android.com/reference/androidx/navigation/Navigation#findNavController(android.view.View)
        Navigation.findNavController(v).navigate(item.getEditAction(), argBundle);
    }
}
