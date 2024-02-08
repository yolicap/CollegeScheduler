package com.example.collegescheduler.ui.list;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.collegescheduler.DataBase;
import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentItemBinding;
import com.example.collegescheduler.item.AbstractItem;
import com.example.collegescheduler.ui.addAssignment.AddAssignment;
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
        holder.mEdit.setOnClickListener(new ItemOnClickListener(holder.mItem));
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.mDelete.getContext());
                builder.setCancelable(true);
                builder.setTitle("Confirm Delete?");
                builder.setMessage("Do you want to delete this item?");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (DataBase.getCourseDict().containsKey(holder.mItem.getId())) {
                            DataBase.removeCourse(holder.mItem.getId());
                            System.out.println(getCurrentList().size());
                        } else if (DataBase.getAssignmentDict().containsKey(holder.mItem.getId())){
                            DataBase.removeAssignment(holder.mItem.getId());
                        } else if (DataBase.getExamDict().containsKey(holder.mItem.getId())) {
                            DataBase.removeExam(holder.mItem.getId());
                        } else {
                            dialog.dismiss();
                            return;
                        }

                        submitList(getCurrentList());
                        Navigation.findNavController(view).navigate(R.id.navigation_list);
                        
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;

        public final Button mEdit;
        public final Button mDelete;
        public AbstractItem mItem;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mEdit = binding.edit;
            mContentView = binding.content;
            mDelete = binding.delete;
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

    }
}