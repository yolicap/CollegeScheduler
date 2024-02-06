package com.example.collegescheduler.ui.list;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentItemBinding;
import com.example.collegescheduler.item.AbstractItem;
import com.example.collegescheduler.ui.addCourse.AddCourse;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link ListAdapter} that can display a {@link AbstractItem}.
 */
public class ItemListAdapter extends ListAdapter<AbstractItem, ItemListAdapter.ViewHolder> {
    public ItemListAdapter(List<AbstractItem> items) {
        super(AbstractItem.DIFF_CALLBACK);
        submitList(items);
    }

    public ItemListAdapter() {super(AbstractItem.DIFF_CALLBACK);}

    @Override
    public void submitList(final List<AbstractItem> list) {
        super.submitList(list != null ? new ArrayList<AbstractItem>(list) : null);
        System.out.println("New list submitted.");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = getItem(position);
//        holder.mIdView.setText(getItem(position).getId());
        holder.mContentView.setText(getItem(position).getName());
        holder.itemView.setOnClickListener(new ItemOnClickListener(holder.mItem));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public AbstractItem mItem;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

    }
}