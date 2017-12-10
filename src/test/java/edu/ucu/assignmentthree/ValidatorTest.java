package edu.ucu.assignmentthree;

import edu.ucu.assignmentthree.conf.AppConf;
import edu.ucu.assignmentthree.validator.EventTimeValidator;
import edu.ucu.assignmentthree.validator.OperationValidator;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static edu.ucu.assignmentthree.conf.Const.DEV;
import static edu.ucu.assignmentthree.Utils.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConf.class)
@ActiveProfiles(DEV)
public class ValidatorTest {
    private static int RED_CARD = 2;
    private static int PASS_SENT = 3;
    private static int KICK_TO_ROD = 5;

    @Autowired
    private JavaSparkContext sc;

    @Autowired
    private EventTimeValidator eventTimeValidator;

    @Autowired
    private OperationValidator operationValidator;

    @Test
    public void testEventTime() throws Exception {
        JavaRDD<Event> rdd = sc.parallelize(Arrays.asList(
                Event.builder().eventTime(buildTime("00:00:00")).build(),
                Event.builder().eventTime(buildTime("01:30:00")).build(),
                Event.builder().eventTime(buildTime("01:40:00")).build(),
                Event.builder().eventTime(buildTime("00:50:00")).build()
        ));

        Assert.assertArrayEquals(
                rdd.map(eventTimeValidator.createValidator()).collect().toArray(),
                new Boolean[] {true, true, false, true});
    }

    @Test
    public void testOperation() throws Exception {
        JavaRDD<Event> rdd = sc.parallelize(Arrays.asList(
                Event.builder().code(RED_CARD).from("Player1").to("Player2").build(),
                Event.builder().code(RED_CARD).from("").to("Player2").build(),
                Event.builder().code(RED_CARD).to("Player2").build(),
                Event.builder().code(PASS_SENT).to("Player2").build(),
                Event.builder().code(PASS_SENT).from("Player1").to("Player2").build(),
                Event.builder().code(PASS_SENT).from("Player1").to("").build(),
                Event.builder().code(KICK_TO_ROD).from("Player1").to("Player2").build(),
                Event.builder().code(KICK_TO_ROD).from("Player1").to("").build(),
                Event.builder().code(KICK_TO_ROD).from("Player1").build()
        ));

        Assert.assertArrayEquals(
                rdd.map(operationValidator.createValidator()).collect().toArray(),
                new Boolean[] {false, true, true, false, true, false, false, true, true});
    }
}
