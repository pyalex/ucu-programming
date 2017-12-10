package edu.ucu.assignmentthree.udf;

import edu.ucu.assignmentthree.util.RegisterUDF;
import lombok.SneakyThrows;
import org.apache.spark.sql.api.java.UDF1;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@RegisterUDF
public class GameTimeEnrichmentUDF implements UDF1<Timestamp, String> {
    @SneakyThrows
    public String call(Timestamp eventTime) {

        // This magic handles timezone issue of Timestamp
        SimpleDateFormat eventFormat = new SimpleDateFormat("mm:ss");
        Timestamp median = new Timestamp(eventFormat.parse("45:00").getTime());

        return eventTime.after(median) ? "second": "first";
    }
}
