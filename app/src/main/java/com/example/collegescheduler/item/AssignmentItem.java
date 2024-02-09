package com.example.collegescheduler.item;

import android.graphics.Movie;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.collegescheduler.R;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;

public class AssignmentItem extends AbstractItem {
    private LocalDate dueDate;
    private LocalTime dueTime;
    private CourseItem course;

    public static final int EDIT_ACTION = R.id.edit_assignment;

    public AssignmentItem(UUID id, String content, String details) {
        super(id, content, details);
    }

    public AssignmentItem(String content, String details) {
        super(content, details);
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalTime getDueTime() {
        return dueTime;
    }

    public void setDueTime(LocalTime dueTime) {
        this.dueTime = dueTime;
    }

    public CourseItem getCourse() {
        return course;
    }

    public void setCourse(CourseItem course) {
        this.course = course;
    }

    @Override
    public int getEditAction() {
        return EDIT_ACTION;
    }

    public static class DueDateComparator implements Comparator<AssignmentItem> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public int compare(AssignmentItem o1, AssignmentItem o2) {
            return o1.getDueDate().compareTo(o2.getDueDate());
        }
    }

    public static class CourseNameComparator implements Comparator<AssignmentItem> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public int compare(AssignmentItem o1, AssignmentItem o2) {
            return o1.getCourse().getName().compareTo(o2.getCourse().getName());
        }
    }
}
