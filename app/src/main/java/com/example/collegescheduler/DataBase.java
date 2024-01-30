package com.example.collegescheduler;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Map;

public class DataBase {

    public static Dictionary<String, String[]> data = new Hashtable<>();

    public static void put(String id, String[] values) {
        data.put(id, values);
    }

    public static String[] remove(String id) {
        String[] values = data.get(id);
        data.remove(id);
        return values;
    }

    public static String[] get(String id) {
        return data.get(id);
    }
}
