package edu.ucu.assignmentthree.conf;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Data
public class ColumnConfig implements Serializable {
    @Value("${columnNames}")
    private String[] columnNames;
}
