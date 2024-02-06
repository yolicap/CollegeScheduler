package com.example.collegescheduler.item;

import com.example.collegescheduler.R;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class AssignmentItem extends AbstractItem{
    private LocalDate dueDate;
    private LocalTime dueTime;
    private CourseItem course;

    public static final int EDIT_ACTION = R.id.edit_assignment;

    public AssignmentItem(UUID id, String content, String details) {
        super(id, content, details);
    }

    public AssignmentItem(String content, String details){super(content, details);}

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
}
