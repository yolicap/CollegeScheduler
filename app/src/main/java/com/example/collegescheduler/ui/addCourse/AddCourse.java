package com.example.collegescheduler.ui.addCourse;

import androidx.lifecycle.ViewModelProvider;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;

import com.example.collegescheduler.DataBase;
import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentAddCourseBinding;
import com.example.collegescheduler.item.CourseItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class AddCourse extends Fragment {

    private AddCourseViewModel mViewModel;
    private FragmentAddCourseBinding binding;

    private Button pickTimeBtn;
    private TextView selectedTimeTV;

    TextView textView;
    boolean[] selectedDays;
    ArrayList<Integer> daysList = new ArrayList<>();
    String[] daysArray = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};


    public static AddCourse newInstance() {
        return new AddCourse();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentAddCourseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
//        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddCourse.this)
                        .navigate(R.id.action_navigation_add_course_to_navigation_dashboard);
            }
        });

        // on below line we are initializing our variables.
        pickTimeBtn = binding.pickCourseTime;
        selectedTimeTV = binding.SelectedCourseTime;

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
                TimePickerDialog timePickerDialog = new TimePickerDialog(binding.pickCourseTime.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // on below line we are setting selected time
                                // in our text view.
                                selectedTimeTV.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
//                 at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });

        // assign variable
        textView = binding.courseDays;

        // initialize selected language array
        selectedDays = new boolean[daysArray.length];

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(binding.addCourse.getContext());

                // set title
                builder.setTitle("Select Course Days");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(daysArray, selectedDays, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            daysList.add(i);
                            // Sort array list
                            Collections.sort(daysList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            daysList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < daysList.size(); j++) {
                            // concat array value
                            stringBuilder.append(daysArray[daysList.get(j)]);
                            // check condition
                            if (j != daysList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        textView.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedDays.length; j++) {
                            // remove all selection
                            selectedDays[j] = false;
                            // clear language list
                            daysList.clear();
                            // clear text view value
                            textView.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });

        Button submitButton = (Button) view.findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                final EditText courseNameEditText = (EditText) view.getRootView().findViewById(R.id.course_name);
                final EditText profNameEditText = (EditText) view.getRootView().getRootView().findViewById(R.id.prof_name);
                final EditText locationNameEditText = (EditText) view.getRootView().findViewById(R.id.location_name);

                if (courseNameEditText == null ||
                        profNameEditText == null ||
                        locationNameEditText == null) {
                    System.out.println("Could not find input fields.");
                    return ;
                }
//        final CourseItem course = DataBase.getCourseByName(
//                courseNameEditText.getText().toString()
//        );
                final String courseName = courseNameEditText.getText().toString();
                final String profName = profNameEditText.getText().toString();
                final String locationName = locationNameEditText.getText().toString();

                if (courseName.isEmpty() ||
                    profName.isEmpty() ||
                    locationName.isEmpty() ){
                    System.out.println("Required fields empty");
                    return ;
                }

                final CourseItem course = new CourseItem(courseName, "");
                course.setProfessor(profName);
                course.setBuilding(locationName);
                DataBase.addCourse(course);

                // TODO : Check if null
                // TODO : Add times
                // TODO : Exit
                System.out.println("Course added!");
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddCourseViewModel.class);
        // TODO: Use the ViewModel
    }

}