package com.example.collegescheduler.ui.home;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.DataBase;
import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentHomeBinding;
import com.example.collegescheduler.databinding.FragmentHomeItemBinding;
import com.example.collegescheduler.databinding.FragmentItemBinding;
import com.example.collegescheduler.item.AbstractItem;
import com.example.collegescheduler.item.AssignmentItem;
import com.example.collegescheduler.item.ExamItem;
import com.example.collegescheduler.ui.list.ItemListAdapter;
import com.example.collegescheduler.ui.list.ItemOnClickListener;

import java.util.ArrayList;
import java.util.List;


public class HomeListAdapter extends ListAdapter<AbstractItem, HomeListAdapter.ViewHolder> {
    public HomeListAdapter(List<AbstractItem> items) {
        super(AbstractItem.DIFF_CALLBACK);
        submitList(items);
    }

    public HomeListAdapter() {super(AbstractItem.DIFF_CALLBACK);}

    @Override
    public void submitList(final List<AbstractItem> list) {
        super.submitList(list != null ? new ArrayList<AbstractItem>(list) : null);
        System.out.println("New list submitted.");
    }

    @NonNull
    @Override
    public HomeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeListAdapter.ViewHolder(FragmentHomeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mItem = getItem(position);
//        holder.mIdView.setText(getItem(position).getId());
        AbstractItem item = getItem(position);
        String type = "";
        if (item instanceof ExamItem) {
            type = "Exam: ";
        } else if (item instanceof AssignmentItem){
            type = "Assignment: ";
        } else {
            type = "ERROR. Search CustomAdapter for this error to debug. ";
        }
        type += getItem(position).getName();
        holder.mContentView.setText(type);

        holder.mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mDone.setBackgroundResource(R.drawable.ic_check);
            }
        });

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
                        if (DataBase.getAssignmentDict().containsKey(holder.mItem.getId())){
                            DataBase.removeAssignment(holder.mItem.getId());
                        } else if (DataBase.getExamDict().containsKey(holder.mItem.getId())) {
                            DataBase.removeExam(holder.mItem.getId());
                        } else {
                            dialog.dismiss();
                            return;
                        }

                        submitList(getCurrentList());
                        Navigation.findNavController(view).navigate(R.id.navigation_home);

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
        public AbstractItem mItem;
        public final Button mEdit;
        public final Button mDelete;

        public final Button mDone;

        public ViewHolder(FragmentHomeItemBinding binding) {
            super(binding.getRoot());
            mIdView = binding.homeItemNumber;
            mContentView = binding.homeContent;
            mEdit = binding.todoEdit;
            mDelete = binding.todoDelete;
            mDone = binding.done;
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

    }
}