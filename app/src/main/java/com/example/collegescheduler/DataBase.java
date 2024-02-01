package com.example.collegescheduler;
import android.content.res.AssetManager;

import com.example.collegescheduler.item.AbstractItem;
import com.example.collegescheduler.item.AssignmentItem;
import com.example.collegescheduler.item.CourseItem;
import com.example.collegescheduler.item.ExamItem;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Map;

import java.util.UUID;

public class DataBase {

    private static Dictionary<UUID, AssignmentItem> assignmentDict = new Hashtable<>();
    private static Dictionary<UUID, CourseItem> courseDict = new Hashtable<>();
    private static Dictionary<UUID, ExamItem> examDict = new Hashtable<>();

    /*
    what an Assignment needs: name, time (specific), class
    what a Course needs: name, time (weekly)
    what an Exam needs: name, time (specific), class
    everything needs a type to specify what it is

    AbstractItem has [name, type]
    [String name, String type, String ]
     */

    public static AssignmentItem getAssignment(UUID id) {
        return assignmentDict.get(id);
    }

    public static CourseItem getCourse(UUID id) {
        return courseDict.get(id);
    }

    public static ExamItem getExam(UUID id) {
        return examDict.get(id);
    }

    public static void removeAssignment(UUID id) {
        assignmentDict.remove(id);
    }

    public static void removeCourse(UUID id) {
        courseDict.remove(id);
    }

    public static void removeExam(UUID id) {
        examDict.remove(id);
    }

    public static Dictionary<UUID, AssignmentItem> getAssignmentDict() {
        return assignmentDict;
    }

    public static Dictionary<UUID, CourseItem> getCourseDict() {
        return courseDict;
    }

    public static Dictionary<UUID, ExamItem> getExamDict() {
        return examDict;
    }
}
