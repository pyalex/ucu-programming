package edu.ucu.assignmentthree.service;

import edu.ucu.assignmentthree.Event;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface EventEnricher {
    public Dataset<Row> enrich(JavaRDD<Event> inputRdd);
}
