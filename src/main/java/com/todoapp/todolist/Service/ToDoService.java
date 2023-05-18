package com.todoapp.todolist.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.todoapp.todolist.Model.ToDo;

@Service
public class ToDoService {

    private static Map<String, ToDo> toDos = new HashMap<>();

    public List<ToDo> getToDos() {
        return new ArrayList<>(toDos.values());
    }

    public ToDo createToDo(ToDo toDo) {
        String id = java.util.UUID.randomUUID().toString();
        toDo.setId(id);
        toDo.setDone(false);
        toDos.put(id, toDo);
        return toDo;
    }
}
