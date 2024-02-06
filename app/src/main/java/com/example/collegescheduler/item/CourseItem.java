package com.example.collegescheduler.item;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CourseItem extends AbstractItem {
    private LocalTime meetingTimes;
    private List<DayOfWeek> daysOfWeek;
    private String professor;
    private String section;

    // for location requirement
    private String building;
    private String room;
    private List<AssignmentItem> assignments;
    private List<ExamItem> exams;
    public CourseItem(UUID id, String content, String details) {
        super(id, content, details);
        this.assignments = new ArrayList<>();
    }

    public CourseItem(String courseName, String content) {
        super(courseName, content);
        this.assignments = new ArrayList<>();
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

    public List<DayOfWeek> getDayOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(List<DayOfWeek> dayOfWeek) {
        this.daysOfWeek = dayOfWeek;
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

    public void addAssignment(AssignmentItem assignmentItem){ this.assignments.add(assignmentItem);}
}
