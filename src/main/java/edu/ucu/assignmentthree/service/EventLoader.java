package edu.ucu.assignmentthree.service;

import org.apache.spark.api.java.JavaRDD;

import java.util.Map;

public interface EventLoader {
    public JavaRDD<Map<String, String>> load();
}
