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


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.UUID;

public class AddAssignment extends Fragment {

    private AddAssignmentViewModel mViewModel;
    private FragmentAddAssignmentBinding binding;

    private AssignmentItem item = null;

    DatePickerDialog picker;
    EditText eText;
    private TextView selectedDueDate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        /*
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        */

        binding = FragmentAddAssignmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Bundle args = getArguments();

        if (args != null) {
            if (!args.getString("item_uuid").isEmpty()) {
                item = DataBase.getAssignment(
                        UUID.fromString(args.getString("item_uuid"))
                );
            }
        }

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.backButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddAssignment.this).popBackStack();
            }
        });

        selectedDueDate = binding.selectedDueDate;

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
                                selectedDueDate.setText(
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
                        final TextView selectedDueDateTextView = (TextView) view.getRootView().findViewById(R.id.selectedDueDate);

                        if (assignmentNameEditText == null ||
                                assignmentCourseNameEditText == null ||
                                selectedDueDateTextView == null) {
                            System.out.println("Could not find input fields.");
                            return;
                        }

                        // required fields
                        final String assignmentName = assignmentNameEditText.getText().toString();

                        if (assignmentName.isEmpty()) {
                            System.out.println("Required fields empty");
                            dialog.dismiss();
                            return;
                        }

                        // optional fields
                        // needs to be formatted properly ?
                        // TODO : format as 00-00-0000
                        final LocalDate date = LocalDate.parse(
                                selectedDueDateTextView.getText().toString().isEmpty() ? "00-00-0000": selectedDueDateTextView.getText()
                        );
                        final CourseItem course = DataBase.getCourseByName(
                                assignmentCourseNameEditText.getText().toString()
                        );
                        if (course == null) {
                            System.out.println("Course not found or does not exist.");
                            return ;
                        }

                        // TODO : builder would be better here

                        if (item != null) {
                            // Set assignment name
                            item.setName(assignmentName);
                            // Set exam date
                            item.setDueDate(date);
                            // Set course
                            item.setCourse(course);
                            return;
                        }

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

        // Yes this is bad coding practice. No we don't have time

        final EditText assignmentNameEditText = (EditText) view.getRootView().findViewById(R.id.assignment_name);
        final EditText assignmentCourseNameEditText = (EditText) view.getRootView().getRootView().findViewById(R.id.assignment_course_name);
        final TextView selectedDueDateTextView = (TextView) view.getRootView().findViewById(R.id.selectedDueDate);

        if (item != null) {
            // Set assignment name
            assignmentNameEditText.setText(
                    item.getName() == null ? "Assignment Name" : item.getName()
            );
            // Set course name
            assignmentCourseNameEditText.setText(
                    item.getCourse() == null ? "Course Name" : item.getCourse().getName()
            );
            // Set due date
            selectedDueDateTextView.setText(
                    item.getDueDate() == null ? "Course Time" : item.getDueDate().toString()
            );
        }


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