package edu.ucu.assignmentthree.conf;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;


@Data
@Builder
public class TeamConfig implements Serializable{
    private final Map<String, String> teams;
}
