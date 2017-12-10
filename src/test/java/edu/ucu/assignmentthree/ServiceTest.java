package edu.ucu.assignmentthree;

import com.google.common.collect.ImmutableMap;
import edu.ucu.assignmentthree.conf.AppConf;
import edu.ucu.assignmentthree.service.EventEnricher;
import edu.ucu.assignmentthree.service.EventParser;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static edu.ucu.assignmentthree.conf.Const.DEV;
import static edu.ucu.assignmentthree.Utils.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConf.class)
@ActiveProfiles(DEV)
public class ServiceTest {
    @Autowired
    private JavaSparkContext sc;

    @Autowired
    private EventParser parser;

    @Autowired
    private EventEnricher enricher;

    @Test
    public void testParser() {

        JavaRDD<Map<String, String>> rdd = sc.parallelize(Arrays.asList(
            ImmutableMap.<String, String>builder().build(),
            ImmutableMap.<String, String>builder().put("code", "1").build(),
            ImmutableMap.<String, String>builder().put("eventTime", "").put("startTime", "19:00").put("code", "999").build(),
            ImmutableMap.<String, String>builder().put("eventTime", "15:11")
                .put("startTime", "19:30").put("code", "999").build(),
            ImmutableMap.<String, String>builder().put("eventTime", "15:11")
                .put("startTime", "19:30").put("code", "999")
                .put("from", "Player1").put("to", "Player2").put("stadion", "Kyiv").build()
        ));

        List<Event> collect = parser.parse(rdd).collect();
         Assert.assertArrayEquals(collect.toArray(), new Event[] {
            Event.builder().startTime(buildTime("19:30:00"))
                .eventTime(buildTime("00:15:11")).code(999).build(),
             Event.builder().startTime(buildTime("19:30:00"))
                 .eventTime(buildTime("00:15:11")).code(999)
                 .stadion("Kyiv").from("Player1").to("Player2").build(),

         });
    }

    @Test
    public void testEnrichment() {
        JavaRDD<Event> rdd = sc.parallelize(Arrays.asList(
            Event.builder().eventTime(buildTime("00:55:00"))
                .startTime(buildTime("19:30:00"))
                .from("Andriy Pyatov").code(5).build()
        ));

        Dataset<Row> df = enricher.enrich(rdd);

        Assert.assertArrayEquals(getColumnValues(df, "team"), new String[] {"Ukraine"});
        Assert.assertArrayEquals(getColumnValues(df, "game time"), new String[] {"second"});
        Assert.assertArrayEquals(getColumnValues(df, "operation"), new String[] {"kick to rod"});
    }

}
