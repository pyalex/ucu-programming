package edu.ucu.assignmentthree.aop;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static edu.ucu.assignmentthree.conf.Const.DEV;
import static edu.ucu.assignmentthree.conf.Const.PROD;

@Component
@Aspect
@Profile(DEV)
public class DebugResultAspect {
    @AfterReturning(value="@annotation(DebugResult)", returning="output")
    public void showOutput(JoinPoint p, Object output) {
        System.out.println("Debug aspect begin");
        if (output instanceof JavaRDD) {

            ((JavaRDD) output).collect().forEach(i -> System.out.println(i));
        }

        if (output instanceof Dataset) {
            ((Dataset) output).show();
        }

        System.out.println("Debug aspect end");
    }

}
