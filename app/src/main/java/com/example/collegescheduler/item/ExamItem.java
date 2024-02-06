package com.example.collegescheduler.item;

import com.example.collegescheduler.R;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

public class ExamItem extends AbstractItem {
    private LocalTime time;
    private String building;
    private String room;
    private CourseItem course;
    public static final int EDIT_ACTION = R.id.edit_exam;
    public ExamItem(UUID id, String content, String details) {
        super(id, content, details);
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
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
