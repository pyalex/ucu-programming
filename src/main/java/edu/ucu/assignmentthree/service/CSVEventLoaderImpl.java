package edu.ucu.assignmentthree.service;

import edu.ucu.assignmentthree.aop.DebugResult;
import edu.ucu.assignmentthree.conf.ColumnConfig;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.*;

import static edu.ucu.assignmentthree.conf.Const.PROD;

// This is definitely not how we would read csv in our real production code
// But for example of Profile usage it should be fine
// So let's imagine this is production version of Data Loader

@Component
@Profile(PROD)
public class CSVEventLoaderImpl implements EventLoader {
    @Autowired
    private JavaSparkContext sc;

    @Autowired
    private ColumnConfig columns;

    private static Map<String, String> zipWithColumnNames(String[] row, Broadcast<ColumnConfig> config) {
        Map<String, String> map = new HashMap<>();
        Iterator<String> keys = Arrays.asList(config.value().getColumnNames()).iterator();
        Iterator<String> values = Arrays.asList(row).iterator();
        while (keys.hasNext() && values.hasNext()) {
            map.put(keys.next(), values.next());
        }
        return map;
    }

    @Override
    @DebugResult
    public JavaRDD<Map<String,String>> load() {
        Broadcast<ColumnConfig> columnConfig = sc.broadcast(columns);
        return sc.textFile("data/prodData.csv").map(line -> {
            String[] row = line.split(",");
            return zipWithColumnNames(row, columnConfig);
        });
    }
}
