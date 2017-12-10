package edu.ucu.assignmentthree.util;

import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Collection;

public class UdfRegisterApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private SQLContext sqlContext;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Collection<Object> udfObjects = context.getBeansWithAnnotation(RegisterUDF.class).values();
        for (Object udfObject : udfObjects) {

            sqlContext.udf().register(udfObject.getClass().getName(),
                    (UDF1<?, ?>) udfObject,
                    DataTypes.StringType);
        }
    }
}
