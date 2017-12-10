package edu.ucu.assignmentthree.validator;

import edu.ucu.assignmentthree.Event;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.broadcast.Broadcast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


@Component
public class OperationValidator implements Validator<Event> {
    private static int YELLOW_CARD = 1;
    private static int RED_CARD = 2;
    private static int PASS_SENT = 3;
    private static int PASS_RECEIVED = 4;
    private static int KICK_TO_ROD = 5;
    private static int KICK_TO_GATE = 6;
    private static int GOAL = 7;
    private static int PENALTY = 9;
    private static int FREE_KICK = 6;

    private Map<Integer, Function<Event, Boolean>> map = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(OperationValidator.class);

    @Autowired
    private JavaSparkContext sc;

    @PostConstruct
    void fillOperations() {
        Function<Event, Boolean> bothRequired = event -> event.getFrom() != null && event.getFrom().length() > 0 &&
                event.getTo() != null && event.getTo().length() > 0;
        Function<Event, Boolean> onlyToRequired = event -> (event.getFrom() == null || event.getFrom().length() == 0) &&
                event.getTo() != null && event.getTo().length() > 0;
        Function<Event, Boolean> onlyFromRequired = event -> event.getFrom() != null && event.getFrom().length() > 0 &&
                (event.getTo() == null || event.getTo().length() == 0);

        map.put(YELLOW_CARD, bothRequired);
        map.put(RED_CARD, onlyToRequired);
        map.put(PASS_SENT, bothRequired);
        map.put(PASS_RECEIVED, bothRequired);
        map.put(KICK_TO_ROD, onlyFromRequired);
        map.put(KICK_TO_GATE, onlyFromRequired);
        map.put(GOAL, bothRequired);
        map.put(PENALTY, bothRequired);
        map.put(FREE_KICK, onlyToRequired);
    }


    public Function<Event, Boolean> createValidator() {
        Broadcast<Map<Integer, Function<Event, Boolean>>> ops = sc.broadcast(map);

        return event -> {
            if (ops.getValue().containsKey(event.getCode())) {
                Boolean result = ops.getValue().get(event.getCode()).call(event);
                if (!result) {
                    logger.warn("{} was filtered out by OperationValidator", event);
                }
                return result;
            }
            return true;
        };
    }
}
