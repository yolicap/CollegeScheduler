package com.example.collegescheduler.ui.addExam;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.collegescheduler.DataBase;
import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentAddAssignmentBinding;
import com.example.collegescheduler.databinding.FragmentAddExamBinding;
import com.example.collegescheduler.item.AssignmentItem;
import com.example.collegescheduler.item.CourseItem;
import com.example.collegescheduler.item.ExamItem;
import com.example.collegescheduler.ui.addAssignment.AddAssignment;
import com.example.collegescheduler.ui.dashboard.DashboardViewModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.UUID;

public class AddExam extends Fragment {

    private AddExamViewModel mViewModel;

    private FragmentAddExamBinding binding;

    DatePickerDialog picker;
    EditText eText;
    TextView tvw;

    private ExamItem item = null;

    private Button pickTimeBtn;
    private TextView selectedTimeTV;
    private TextView selectedExamDate;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentAddExamBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Bundle args = getArguments();

        if (args != null) {
            if (!args.getString("item_uuid").isEmpty()) {
                item = DataBase.getExam(
                        UUID.fromString(args.getString("item_uuid"))
                );
            }
        }

        // on below line we are initializing our variables.
        pickTimeBtn = binding.pickTime;
        selectedTimeTV = binding.SelectedExamTime;

        // on below line we are adding click
        // listener for our pick date button
        pickTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting the
                // instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting our hour, minute.
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // on below line we are initializing our Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(root.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // on below line we are setting selected time
                                // in our text view.

                                selectedTimeTV.setText(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, hour, minute, false);
                // at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });

        return root;


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.backButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddExam.this).popBackStack();
            }
        });

        selectedExamDate = binding.selectedExamDate;


        binding.pickExamDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(binding.pickExamDate.getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                selectedExamDate.setText(
                                        String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth)
                                );                            }
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
                builder.setMessage("Do you want to add this exam?");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    // needs to be extracted
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final EditText examNameEditText = (EditText) view.getRootView().findViewById(R.id.exam_name);
                        final EditText examCourseNameEditText = (EditText) view.getRootView().getRootView().findViewById(R.id.exam_course_name);
                        final TextView selectedExamDateTextView = (TextView) view.getRootView().findViewById(R.id.selectedExamDate);
                        final TextView SelectedExamTimeTextView = (TextView) view.getRootView().findViewById(R.id.SelectedExamTime);
                        final EditText locationNameEditText = (EditText) view.getRootView().findViewById(R.id.exam_location);

                        if (examNameEditText == null ||
                                examCourseNameEditText == null ||
                                selectedExamDateTextView == null ||
                                SelectedExamTimeTextView == null ||
                                locationNameEditText == null ) {
                            System.out.println("Could not find input fields.");
                        }

                        // required fields
                        final String examName = examNameEditText.getText().toString();

                        if (examName.isEmpty()) {
                            System.out.println("Required fields empty");
                            dialog.dismiss();
                            return;
                        }

                        // optional fields
                        // needs to be formatted properly ?
                        final LocalDate date = LocalDate.parse(
                                selectedExamDateTextView.getText().toString().isEmpty() ? "00/00/0000": selectedExamDateTextView.getText()
                        );

                        final LocalTime time = LocalTime.parse(
                                SelectedExamTimeTextView.getText().toString().equals("Course Time") ? "00:00" : SelectedExamTimeTextView.getText()
                        );
                        final CourseItem course = DataBase.getCourseByName(
                                examCourseNameEditText.getText().toString()
                        );

                        final String locationName = locationNameEditText.getText().toString();

                        // TODO : builder would be better here

                        if (item != null) {
                            // Set exam name
                            item.setName(examName);
                            // Set exam date
                            item.setExamDate(date);
                            // Set location
                            item.setBuilding(locationName);
                            // Set time
                            item.setTime(time);
                            return;
                        }

                        final ExamItem exam = new ExamItem(examName, "");
                        exam.setExamDate(date);
                        exam.setBuilding(locationName);
                        exam.setTime(time);
                        if (course != null) {
                            exam.setCourse(course);
                            course.addExam(exam);
                        }
                        DataBase.addExam(exam);

                        // TODO : exit page
                        System.out.println("Exam added!");
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

        final EditText examNameEditText = (EditText) view.getRootView().findViewById(R.id.exam_name);
        final EditText examCourseNameEditText = (EditText) view.getRootView().getRootView().findViewById(R.id.exam_course_name);
        final TextView selectedExamDateTextView = (TextView) view.getRootView().findViewById(R.id.selectedExamDate);
        final TextView SelectedExamTimeTextView = (TextView) view.getRootView().findViewById(R.id.SelectedExamTime);
        final EditText locationNameEditText = (EditText) view.getRootView().findViewById(R.id.exam_location);

        if (item != null) {
            // Set exam name
            examNameEditText.setText(
                    item.getName() == null ? "Exam Name" : item.getName()
            );
            // Set course name
            examCourseNameEditText.setText(
                    item.getCourse() == null ? "Course Name" : item.getCourse().getName()
            );
            // Set due date
            selectedExamDateTextView.setText(
                    item.getExamDate() == null ? "Course Time" : item.getExamDate().toString()
            );
            // Set location
            locationNameEditText.setText(
                    item.getBuilding() == null ? "Location" : item.getBuilding()
            );
            // Set exam time
            SelectedExamTimeTextView.setText(
                    item.getTime() == null ? "Course Time" : item.getTime().toString()
            );
        }


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddExamViewModel.class);
        // TODO: Use the ViewModel
    }

}