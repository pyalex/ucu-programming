package edu.ucu.assignmentthree.service;

import edu.ucu.assignmentthree.aop.DebugResult;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static edu.ucu.assignmentthree.conf.Const.DEV;


@Service
@Profile(DEV)
public class LocalEventLoaderImpl implements EventLoader {
    @Autowired
    private JavaSparkContext sc;

    private static Map<String, String> lineToMap(String line) {
        String[] items = line.split(";");
        Map<String, String> values = new HashMap<>();
        for (String item : items) {
            String[] split = item.split("=");
            if (split.length == 2) {
                values.put(split[0], split[1]);
            }
        }
        return values;
    }

    @Override
    @DebugResult
    public JavaRDD<Map<String, String>> load() {
        JavaRDD<String> rdd = sc.textFile("data/rawData.txt");
        return rdd.map(LocalEventLoaderImpl::lineToMap);
    }
}
