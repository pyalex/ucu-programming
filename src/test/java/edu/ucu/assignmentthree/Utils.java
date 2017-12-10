package edu.ucu.assignmentthree;

import lombok.SneakyThrows;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static org.apache.spark.sql.functions.col;

public class Utils {
    @SneakyThrows
    public static Timestamp buildTime(String str) {
        SimpleDateFormat eventFormat = new SimpleDateFormat("hh:mm:ss");
        return new Timestamp(eventFormat.parse(str).getTime());
    }

    public static String[] getColumnValues(Dataset<Row> df, String col) {
        JavaRDD<Row> rdd = df.select(col(col)).toJavaRDD();
        return rdd.map(row -> row.getString(0)).collect().toArray(new String[0]);
    }
}
