package com.todoapp.todolist.Model;

import lombok.Data;

@Data
public class Metrics {
    private String averageTime;
    private String lowPriorityAverageTime;
    private String mediumPriorityAverageTime;
    private String highPriorityAverageTime;
}
