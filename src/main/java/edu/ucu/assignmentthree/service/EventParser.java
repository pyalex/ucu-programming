package edu.ucu.assignmentthree.service;

import edu.ucu.assignmentthree.Event;
import org.apache.spark.api.java.JavaRDD;

import java.util.Map;

public interface EventParser {
    JavaRDD<Event> parse(JavaRDD<Map<String, String>> inputRDD);
}
