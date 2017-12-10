package edu.ucu.assignmentthree.udf;

import edu.ucu.assignmentthree.conf.TeamConfig;
import edu.ucu.assignmentthree.util.AutowiredBroadcast;
import edu.ucu.assignmentthree.util.RegisterUDF;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.api.java.UDF1;


@RegisterUDF
public class TeamEnrichmentUDF implements UDF1<String,String> {
    @AutowiredBroadcast
    private Broadcast<TeamConfig> broadcast;

    @Override
    public String call(String player) throws Exception {
        return broadcast.value().getTeams().get(player);
    }
}
