package edu.ucu.assignmentthree.conf;

import edu.ucu.assignmentthree.util.AutowiredBroadcastBPP;
import edu.ucu.assignmentthree.util.UdfRegisterApplicationListener;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SQLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = {"edu.ucu.assignmentthree.*"})
@PropertySources({
        @PropertySource(name="teams", value="classpath:teams.properties"),
        @PropertySource(name="codes", value="classpath:codes.properties"),
        @PropertySource(name="columns", value="classpath:football_columns.properties")
})
@EnableAspectJAutoProxy
public class AppConf {
    @Bean
    public SparkConf sparkConf() {
        SparkConf sparkConf = new SparkConf();
        sparkConf.setAppName("Football Match Analyzer");
        sparkConf.setMaster("local[*]");
        return sparkConf;
    }

    @Bean
    public JavaSparkContext sc(){
        return new JavaSparkContext(sparkConf());
    }

    @Bean
    public SQLContext sqlContext(){
        return new SQLContext(sc());
    }

    @Bean
    public AutowiredBroadcastBPP autowiredBroadcastBPP() {
        return new AutowiredBroadcastBPP();
    }

    @Bean
    public UdfRegisterApplicationListener udfRegisterApplicationListener() {
        return new UdfRegisterApplicationListener();
    }

    @Autowired
    private Environment env;

    private Map<String, String> getAllProperties(String sourceName) {
        Map<String, String> properties = new HashMap<>();

        if (env instanceof AbstractEnvironment) {
            Object source = ((AbstractEnvironment) env).getPropertySources().get(sourceName);
            if (source instanceof EnumerablePropertySource<?>) {
                String[] propertyNames = ((EnumerablePropertySource) source).getPropertyNames();
                for (String property: propertyNames) {
                    properties.put(property, (String)((EnumerablePropertySource) source).getProperty(property));
                }
            }

        }
        return properties;
    }

    @Bean
    public TeamConfig teamConfig(){
        Map<String, String> teams = new HashMap<>();
        Map<String, String> playersByCountry = getAllProperties("teams");
        for (Map.Entry<String, String> country: playersByCountry.entrySet()) {
            String team = country.getKey();
            String[] players = country.getValue().split(",");

            for (String player: players) {
                teams.put(player, team);
            }
        }
        return TeamConfig.builder().teams(teams).build();
    }

    @Bean
    public CodeConfig codeConfig(){
        Map<Integer, String> codes = new HashMap<>();
        Map<String, String> stringCodes = getAllProperties("codes");
        for (Map.Entry<String, String> code: stringCodes.entrySet()) {
            codes.put(Integer.parseInt(code.getKey()), code.getValue());
        }
        return CodeConfig.builder().codes(codes).build();
    }
}
