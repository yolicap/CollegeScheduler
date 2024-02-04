package com.example.collegescheduler;
import android.content.res.AssetManager;

import com.example.collegescheduler.item.AbstractItem;
import com.example.collegescheduler.item.AssignmentItem;
import com.example.collegescheduler.item.CourseItem;
import com.example.collegescheduler.item.ExamItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import java.util.UUID;
import java.util.function.Predicate;

public class DataBase {

    // TODO should be final
    private static Hashtable<UUID, AssignmentItem> assignmentDict = new Hashtable<>();
    private static Hashtable<UUID, CourseItem> courseDict = new Hashtable<>();
    private static Hashtable<UUID, ExamItem> examDict = new Hashtable<>();

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
    public static CourseItem getCourseByName(String name){
        return FindUtils.findByProperty(getCoursesList(), course -> name.equals(course.getName()));
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

    public static void addCourse(CourseItem course){courseDict.put(course.getId(), course);}
    public static void addExam(ExamItem exam){examDict.put(exam.getId(), exam);}
    public static void addAssignment(AssignmentItem assignment){assignmentDict.put(assignment.getId(), assignment);}

    // TODO: pass dict reference?
    public static Dictionary<UUID, AssignmentItem> getAssignmentDict() {
        return assignmentDict;
    }

    public static Dictionary<UUID, CourseItem> getCourseDict() {
        return courseDict;
    }

    public static Dictionary<UUID, ExamItem> getExamDict() {
        return examDict;
    }

    public static List<AssignmentItem> getAssignmentsList() {
        return new ArrayList<AssignmentItem>(assignmentDict.values());
    }

    public static List<CourseItem> getCoursesList() {
        return new ArrayList<CourseItem>(courseDict.values());
    }

    public static List<ExamItem> getExamsList() {
        return new ArrayList<ExamItem>(examDict.values());
    }
}

// create protected class when refactoring database to package
final class FindUtils {
    public static <T> T findByProperty(Collection<T> col, Predicate<T> filter) {
        return col.stream().filter(filter).findFirst().orElse(null);
    }
}
