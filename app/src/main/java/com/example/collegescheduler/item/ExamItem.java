package com.example.collegescheduler.item;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class ExamItem extends AbstractItem {
    private LocalTime time;
    private String building;
    private String room;
    private CourseItem course;
    public ExamItem(String id, String content, String details) {
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
}
