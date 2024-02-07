package com.example.collegescheduler.ui.addCourse;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
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
import com.example.collegescheduler.item.AbstractItem;
import com.example.collegescheduler.item.CourseItem;
import com.example.collegescheduler.ui.addCourse.AddCourseViewModel;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class AddCourse extends Fragment {

    private AddCourseViewModel mViewModel;
    private FragmentAddCourseBinding binding;

    private Button pickTimeBtn;
    private TextView selectedTimeTV;

    private CourseItem item = null;

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
        Bundle args = getArguments();

        if (args != null) {
            if (!args.getString("item_uuid").isEmpty()) {
                item = DataBase.getCourse(
                        UUID.fromString(args.getString("item_uuid"))
                );
            }
        }

        return root;
    }

    // TODO : need to extract methods and inline classes
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
        selectedTimeTV = binding.selectedCourseTime;

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
                                selectedTimeTV.setText(
                                        String.format("%02d:%02d", hourOfDay, minute)
                                );
                            }
                        }, hour, minute, false);
//                 at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });

        // assign variable
        textView = binding.courseDays;

        // initialize selected days array
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
                            // Add position  in day list
                            daysList.add(i);
                            // Sort array list
                            Collections.sort(daysList);
                        } else {
                            // when checkbox unselected
                            // Remove position from daysList
                            daysList.remove(Integer.valueOf(i));
                        }
                    }
                });

                // TODO : this can easily be extracted since it's used by all add pages
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
                                // to days list size - 1
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

        // TODO : class can easily be extracted since it's used by all add pages
        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(binding.submitButton.getContext());
                builder.setCancelable(true);
                builder.setTitle("Confirm Add!");
                builder.setMessage("Do you want to add this course?");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    // needs to be extracted
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final EditText courseNameEditText = (EditText) view.getRootView().findViewById(R.id.course_name);
                        final EditText profNameEditText = (EditText) view.getRootView().getRootView().findViewById(R.id.prof_name);
                        final EditText locationNameEditText = (EditText) view.getRootView().findViewById(R.id.location_name);
                        final TextView selectTimeTextView = (TextView) view.getRootView().findViewById(R.id.selectedCourseTime);
                        final TextView courseDaysTextView = (TextView) view.getRootView().findViewById(R.id.course_days);

                        if (courseNameEditText == null ||
                                profNameEditText == null ||
                                locationNameEditText == null ||
                                selectTimeTextView == null ||
                                courseDaysTextView == null) {
                            System.out.println("Could not find input fields.");
                            return;
                        }
//                      final CourseItem course = DataBase.getCourseByName(
//                          courseNameEditText.getText().toString()
//                      );

                        // required fields
                        final String courseName = courseNameEditText.getText().toString();
                        final String profName = profNameEditText.getText().toString();
                        final String locationName = locationNameEditText.getText().toString();

                        if (courseName.isEmpty() ||
                                profName.isEmpty() ||
                                locationName.isEmpty()) {
                            System.out.println("Required fields empty");
                            dialog.dismiss();
                            return;
                        }

                        // optional fields
                        final List<DayOfWeek> daysOfWeek = new ArrayList<DayOfWeek>();
                        Arrays.stream(
                                courseDaysTextView.getText().toString().split(", ")
                        ).forEach(
                                dayString -> {if (!dayString.isEmpty()) daysOfWeek.add( DayOfWeek.valueOf(dayString.toUpperCase()) );}
                        );

                        final LocalTime time = LocalTime.parse(
                                selectTimeTextView.getText().toString().equals("Course Time") ? "00:00" : selectTimeTextView.getText()
                        );



                        // TODO : builder would be better here
                        final CourseItem course = new CourseItem(courseName, "");
                        course.setProfessor(profName);
                        course.setBuilding(locationName);
                        course.setDaysOfWeek(daysOfWeek);
                        course.setMeetingTimes(time);
                        DataBase.addCourse(course);

                        // TODO : exit page
                        System.out.println(DataBase.getCoursesList());
                        System.out.println("Course added!");
                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
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
        final EditText courseNameEditText = (EditText) view.getRootView().findViewById(R.id.course_name);
        final EditText profNameEditText = (EditText) view.getRootView().getRootView().findViewById(R.id.prof_name);
        final EditText locationNameEditText = (EditText) view.getRootView().findViewById(R.id.location_name);
        final TextView selectTimeTextView = (TextView) view.getRootView().findViewById(R.id.selectedCourseTime);
        final TextView courseDaysTextView = (TextView) view.getRootView().findViewById(R.id.course_days);

        if (item != null) {
            // Set course name
            courseNameEditText.setText(
                    item.getName() == null ? "Course Name" : item.getName()
            );
            // Set professor name
            profNameEditText.setText(
                    item.getProfessor() == null ? "Professor Name" : item.getProfessor()
            );
            // Set selected days
            String displayDays = item.getDayOfWeek().isEmpty() ? "Select Days" : "";
            for (DayOfWeek dayOfWeek : item.getDayOfWeek()){
                displayDays += dayOfWeek.toString().toLowerCase();
                displayDays += ", ";
            }
            courseDaysTextView.setText(displayDays);
            // Set location
            locationNameEditText.setText(
                    item.getBuilding() == null ? "Location" : item.getBuilding()
            );
            // Set time
            selectTimeTextView.setText(
                    item.getMeetingTimes() == null ? "Course Time" : item.getMeetingTimes().toString()
            );
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddCourseViewModel.class);
        // TODO: Use the ViewModel
    }
}