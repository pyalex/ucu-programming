package edu.ucu.assignmentthree;

import edu.ucu.assignmentthree.conf.AppConf;
import edu.ucu.assignmentthree.conf.CodeConfig;
import edu.ucu.assignmentthree.conf.TeamConfig;
import edu.ucu.assignmentthree.udf.GameTimeEnrichmentUDF;
import edu.ucu.assignmentthree.udf.OperationEnrichmentUDF;
import edu.ucu.assignmentthree.udf.TeamEnrichmentUDF;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.apache.spark.sql.functions.*;

import static edu.ucu.assignmentthree.conf.Const.DEV;
import static edu.ucu.assignmentthree.Utils.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConf.class)
@ActiveProfiles(DEV)
public class UDFTest {
    @Autowired
    private JavaSparkContext sc;

    @Autowired
    private SQLContext sqlContext;

    @Autowired
    private CodeConfig codeConfig;

    @Autowired
    private TeamConfig teamConfig;

    @Test
    public void gameTimeTest() {
        JavaRDD<Event> rdd = sc.parallelize(Arrays.asList(
            Event.builder().eventTime(buildTime("00:00:00")).build(),
            Event.builder().eventTime(buildTime("00:44:00")).build(),
            Event.builder().eventTime(buildTime("00:55:00")).build(),
            Event.builder().eventTime(buildTime("01:12:00")).build()
        ));

        Dataset<Row> df = sqlContext.createDataFrame(rdd, Event.class);
        Dataset<Row> res = df.withColumn("game time", callUDF(
            GameTimeEnrichmentUDF.class.getName(), col("eventTime")
        ));

        Assert.assertArrayEquals(getColumnValues(res, "game time"),
            new String[]{"first", "first", "second", "second"});
    }

    @Test
    public void operationEnrichmentTest() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "kick");
        map.put(2, "fire");

        ReflectionTestUtils.setField(codeConfig, "codes", map);
        JavaRDD<Event> rdd = sc.parallelize(Arrays.asList(
            Event.builder().code(1).build(),
            Event.builder().code(2).build(),
            Event.builder().code(1).build(),
            Event.builder().code(3).build(),
            Event.builder().build()
        ));

        Dataset<Row> df = sqlContext.createDataFrame(rdd, Event.class);
        Dataset<Row> res = df.withColumn("operation", callUDF(
            OperationEnrichmentUDF.class.getName(), col("code")
        ));

        Assert.assertArrayEquals(
            getColumnValues(res, "operation"),
            new String[]{"kick", "fire", "kick", null, null}
        );
    }

    @Test
    public void teamEnrichmentTest() {
        Map<String, String> mock = new HashMap<>();
        mock.put("Andrii Shevchenko", "Ukraine");
        mock.put("Zlatan Ibrahimovic", "Sweden");

        ReflectionTestUtils.setField(teamConfig, "teams", mock);
        JavaRDD<Event> rdd = sc.parallelize(Arrays.asList(
            Event.builder().from("Andrii Shevchenko").build(),
            Event.builder().from("Ruslan Rotan").to("Andrii Shevchenko").build(),
            Event.builder().to("Ruslan Rotan").build(),
            Event.builder().to("Zlatan Ibrahimovic").build(),
            Event.builder().build()
        ));

        Dataset<Row> df = sqlContext.createDataFrame(rdd, Event.class);
        Dataset<Row> res = df.withColumn("team",
            coalesce(
                callUDF(TeamEnrichmentUDF.class.getName(), col("from")),
                callUDF(TeamEnrichmentUDF.class.getName(), col("to"))
            )
        );

        Assert.assertArrayEquals(
            getColumnValues(res, "team"),
            new String[]{"Ukraine", "Ukraine", null, "Sweden", null}
        );

    }
}
