package edu.ucu.assignmentthree.validator;

import edu.ucu.assignmentthree.Event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;


import org.apache.spark.api.java.function.Function;

@Component
public class EventTimeValidator implements Validator<Event> {
    private static final int PLAY_LENGTH_MINUTES = 90;

    private static final Logger logger = LoggerFactory.getLogger(EventTimeValidator.class);

    public Function<Event, Boolean> createValidator() {
        return event -> {
            Timestamp time = event.getEventTime();
            if (time == null) {
                logger.warn("{} was filtered out by EventTimeValidator", event);
                return false;
            }

            Integer totalMinutes = time.getMinutes() + time.getHours() * 60;
            Boolean result = totalMinutes <= PLAY_LENGTH_MINUTES;

            if (!result) {
                logger.warn("{} was filtered out by EventTimeValidator", event);
            }
            return result;
        };
    }
}
