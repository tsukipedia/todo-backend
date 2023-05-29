package com.todoapp.todolist.Model;

import java.util.List;

import lombok.Data;

@Data
public class ToDoListResponse {
    private List<ToDoDTO> list;
    private Integer totalToDos;
}
