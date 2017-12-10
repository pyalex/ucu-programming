package edu.ucu.assignmentthree;

import edu.ucu.assignmentthree.conf.AppConf;
import edu.ucu.assignmentthree.service.MatchAnalyzer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static edu.ucu.assignmentthree.conf.Const.DEV;
import static edu.ucu.assignmentthree.conf.Const.PROD;

public class Main {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", DEV);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConf.class);
        MatchAnalyzer analyzer = context.getBean(MatchAnalyzer.class);
        analyzer.analyze();
    }
}
