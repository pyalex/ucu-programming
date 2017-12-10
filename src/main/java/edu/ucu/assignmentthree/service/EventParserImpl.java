package edu.ucu.assignmentthree.service;

import edu.ucu.assignmentthree.Event;
import edu.ucu.assignmentthree.aop.DebugResult;
import edu.ucu.assignmentthree.validator.Validator;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


@Service
public class EventParserImpl implements EventParser {
    @Autowired
    private List<Validator<Event>> validators;

    private static Optional<Event> parseEvent(Map<String, String> map) {
        Timestamp eventTime, startTime;
        Integer code;
        SimpleDateFormat eventFormat = new SimpleDateFormat("mm:ss");
        SimpleDateFormat startFormat = new SimpleDateFormat("HH:mm");

        try {
            eventTime = new Timestamp(eventFormat.parse(map.getOrDefault("eventTime", "")).getTime());
            startTime = new Timestamp(startFormat.parse(map.getOrDefault("startTime", "")).getTime());
        } catch (ParseException exc) {
            return Optional.empty();
        }

        try {
            code = Integer.parseInt(map.getOrDefault("code", ""));
        } catch (NumberFormatException exc) {
            return Optional.empty();
        }

        Event event = Event.builder().
            code(code).
            eventTime(eventTime).
            startTime(startTime).
            from(map.get("from")).
            to(map.get("to")).
            stadion(map.get("stadion")).
            build();

        return Optional.of(event);
    }

    @Override
    @DebugResult
    public JavaRDD<Event> parse(JavaRDD<Map<String, String>> inputRDD) {
        JavaRDD<Event> rdd = inputRDD.
            map(EventParserImpl::parseEvent).
            filter(Optional::isPresent).  // emulate flatMap with Optional. I miss Scala :(
            map(Optional::get);

        for (Validator<Event> v : validators) {
            rdd = rdd.filter(v.createValidator());
        }
        return rdd;
    }
}