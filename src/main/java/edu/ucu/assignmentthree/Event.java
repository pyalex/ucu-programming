package edu.ucu.assignmentthree;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Builder
public class Event implements Serializable {
    private final int code;
    private final String from;
    private final String to;
    private final Timestamp eventTime;
    private final String stadion;
    private final Timestamp startTime;
}
