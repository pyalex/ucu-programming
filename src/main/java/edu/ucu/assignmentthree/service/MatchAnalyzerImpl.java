package edu.ucu.assignmentthree.service;

import edu.ucu.assignmentthree.Event;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class MatchAnalyzerImpl implements MatchAnalyzer {

    @Autowired
    private EventLoader loader;

    @Autowired
    private EventParser parser;

    @Autowired
    private EventEnricher enricher;

    public void analyze() {
        JavaRDD<Map<String, String>> rdd = loader.load();
        JavaRDD<Event> eventRDD = parser.parse(rdd);
        Dataset<Row> df = enricher.enrich(eventRDD);
        df.show();
    }
}
