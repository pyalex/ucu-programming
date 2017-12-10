package edu.ucu.assignmentthree.conf;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
public class CodeConfig implements Serializable {
    private final Map<Integer, String> codes;
}
