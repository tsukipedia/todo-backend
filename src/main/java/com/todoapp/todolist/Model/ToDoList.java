package com.todoapp.todolist.Model;

import java.util.List;

import lombok.Data;

@Data
public class ToDoList {
    private List<ToDo> list;
    private Integer totalToDos;
}
