package edu.ucu.assignmentthree.udf;

import edu.ucu.assignmentthree.conf.CodeConfig;
import edu.ucu.assignmentthree.util.AutowiredBroadcast;
import edu.ucu.assignmentthree.util.RegisterUDF;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.api.java.UDF1;


@RegisterUDF
public class OperationEnrichmentUDF implements UDF1<Integer, String> {
    @AutowiredBroadcast
    private Broadcast<CodeConfig> codes;

    @Override
    public String call(Integer code) {
        return codes.getValue().getCodes().get(code);
    }
}
