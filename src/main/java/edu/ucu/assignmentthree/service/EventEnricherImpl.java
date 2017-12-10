package edu.ucu.assignmentthree.service;

import edu.ucu.assignmentthree.Event;
import edu.ucu.assignmentthree.aop.DebugResult;
import edu.ucu.assignmentthree.udf.GameTimeEnrichmentUDF;
import edu.ucu.assignmentthree.udf.OperationEnrichmentUDF;
import edu.ucu.assignmentthree.udf.TeamEnrichmentUDF;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.apache.spark.sql.functions.*;


@Service
public class EventEnricherImpl implements EventEnricher {
    @Autowired
    private SQLContext sqlContext;

    @Override
    //@DebugResult
    public Dataset<Row> enrich(JavaRDD<Event> inputRdd) {
        Dataset<Row> df = sqlContext.createDataFrame(inputRdd, Event.class);

        return df.withColumn("team", coalesce(
                callUDF(TeamEnrichmentUDF.class.getName(), col("from")),
                callUDF(TeamEnrichmentUDF.class.getName(), col("to"))
        )).withColumn("operation", callUDF(
                OperationEnrichmentUDF.class.getName(), col("code")
        )).withColumn("game time", callUDF(
                GameTimeEnrichmentUDF.class.getName(), col("eventTime")
        ));
    }
}
