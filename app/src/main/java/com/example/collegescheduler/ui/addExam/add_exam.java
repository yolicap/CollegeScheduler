package com.example.collegescheduler.ui.addExam;

import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import com.example.collegescheduler.MainActivity;
import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentAddAssignmentBinding;
import com.example.collegescheduler.databinding.FragmentAddExamBinding;
import com.example.collegescheduler.ui.addAssignment.add_assignment;
import com.example.collegescheduler.ui.dashboard.DashboardFragment;
import com.example.collegescheduler.ui.dashboard.DashboardViewModel;

import java.util.Calendar;

public class add_exam extends Fragment {

    private AddExamViewModel mViewModel;

    private FragmentAddExamBinding binding;

    DatePickerDialog picker;
    EditText eText;
    TextView tvw;

    private Button pickTimeBtn;
    private TextView selectedTimeTV;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentAddExamBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //setContentView(R.layout.fragment_add_assignment);
        tvw= binding.dateViewExam;
        eText= binding.editTextExamDate;
        eText.setInputType(InputType.TYPE_NULL);

        // on below line we are initializing our variables.
        pickTimeBtn = binding.pickTime;
        selectedTimeTV = binding.SelectedTime;

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
                                selectedTimeTV.setText(hourOfDay + ":" + minute);
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
                NavHostFragment.findNavController(add_exam.this)
                        .navigate(R.id.action_navigation_add_assignment_to_navigation_dashboard);
            }
        });

        binding.pickExamDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(binding.pickExamDate.getContext(),
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
        mViewModel = new ViewModelProvider(this).get(AddExamViewModel.class);
        // TODO: Use the ViewModel
    }

}