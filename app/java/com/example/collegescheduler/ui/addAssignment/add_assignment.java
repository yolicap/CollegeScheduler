package com.example.collegescheduler.ui.addAssignment;

import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentAddAssignmentBinding;
import com.example.collegescheduler.ui.addCourse.Add_Course;
import com.example.collegescheduler.ui.dashboard.DashboardViewModel;


import java.util.Calendar;

public class add_assignment extends Fragment {

    private AddAssignmentViewModel mViewModel;
    private FragmentAddAssignmentBinding binding;

    DatePickerDialog picker;
    EditText eText;
    TextView tvw;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentAddAssignmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //setContentView(R.layout.fragment_add_assignment);
        tvw= binding.dateView;
        eText= binding.editTextDate;
        eText.setInputType(InputType.TYPE_NULL);

        return root;


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.backButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(add_assignment.this)
                        .navigate(R.id.action_navigation_add_assignment_to_navigation_dashboard);
            }
        });

        binding.pickAssignmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(binding.pickAssignmentDate.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddAssignmentViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}