package com.example.collegescheduler.ui.addAssignment;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.collegescheduler.DataBase;
import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentAddAssignmentBinding;
import com.example.collegescheduler.item.AssignmentItem;
import com.example.collegescheduler.item.CourseItem;
import com.example.collegescheduler.ui.dashboard.DashboardViewModel;


import java.time.LocalDate;
import java.util.Calendar;

public class AddAssignment extends Fragment {

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
                NavHostFragment.findNavController(AddAssignment.this)
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
                                eText.setText(
                                        String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth)
                                );
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(binding.submitButton.getContext());
                builder.setCancelable(true);
                builder.setTitle("Confirm Add!");
                builder.setMessage("Do you want to add this assignment?");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // add submit function
                        final EditText assignmentNameEditText = (EditText) view.getRootView().findViewById(R.id.assignment_name);
                        final EditText assignmentCourseNameEditText = (EditText) view.getRootView().getRootView().findViewById(R.id.assignment_course_name);
                        final EditText dateEditText = (EditText) view.getRootView().findViewById(R.id.editTextDate);

                        if (assignmentNameEditText == null ||
                                assignmentCourseNameEditText == null ||
                                dateEditText == null) {
                            System.out.println("Could not find input fields.");
                            return;
                        }

                        // required fields
                        final String assignmentName = assignmentNameEditText.getText().toString();

                        // optional fields
                        // needs to be formatted properly ?
                        final LocalDate date = LocalDate.parse(
                                dateEditText.getText().toString().isEmpty() ? "00/00/0000": dateEditText.getText()
                        );
                        final CourseItem course = DataBase.getCourseByName(
                                assignmentCourseNameEditText.getText().toString()
                        );

                        if (assignmentName.isEmpty()) {
                            System.out.println("Required fields empty");
                            return;
                        }

                        // TODO : builder would be better here
                        final AssignmentItem assignment = new AssignmentItem(assignmentName, "");
                        assignment.setDueDate(date);
                        if (course != null) {
                            assignment.setCourse(course);
                            course.addAssignment(assignment);
                        }
                        DataBase.addAssignment(assignment);

                        // TODO : exit page
                        System.out.println("Assignment added!");
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