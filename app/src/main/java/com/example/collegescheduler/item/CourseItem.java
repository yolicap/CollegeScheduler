package com.example.collegescheduler.item;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class CourseItem extends AbstractItem {
    private LocalTime meetingTimes;
    private DayOfWeek dayOfWeek;
    private String professor;
    private String section;

    // for location requirement
    private String building;
    private String room;
    private List<AssignmentItem> assignments;
    private List<ExamItem> exams;
    public CourseItem(String id, String content, String details) {
        super(id, content, details);
    }
    public LocalDateTime getNextMeeting(){
        // TODO
        return null;
    }

    public LocalTime getMeetingTimes() {
        return meetingTimes;
    }

    public void setMeetingTimes(LocalTime meetingTimes) {
        this.meetingTimes = meetingTimes;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
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

    public List<AssignmentItem> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<AssignmentItem> assignments) {
        this.assignments = assignments;
    }

    public List<ExamItem> getExams() {
        return exams;
    }

    public void setExams(List<ExamItem> exams) {
        this.exams = exams;
    }
}
