package com.todoapp.todolist.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.todoapp.todolist.Model.Priority;
import com.todoapp.todolist.Model.ToDo;
import com.todoapp.todolist.Model.ToDoDTO;

@Service
public class ToDoService {

    private static Map<String, ToDo> toDos = new HashMap<>();

    static {
        Date due = new GregorianCalendar(2023, 5, 31, 23, 59).getTime();
        String id1 = java.util.UUID.randomUUID().toString();
        String id2 = java.util.UUID.randomUUID().toString();

        ToDo toDo1 = new ToDo();
        ToDo toDo2 = new ToDo();
        
        toDo1.setId(id1);
        toDo1.setContent("delectus aut autem");
        toDo1.setDone(false);
        toDo1.setPriority(Priority.MEDIUM);

        toDo2.setId(id2);
        toDo2.setContent("quis ut nam facilis et officia qui");
        toDo2.setDone(false);
        toDo2.setDueDate(due);

        toDos.put(id1, toDo1);
        toDos.put(id2, toDo2);
    }

    public List<ToDoDTO> getToDos() throws ParseException {
        List<ToDoDTO> todoDTOs = new ArrayList<>();

        for (ToDo todo : toDos.values()) {
            todoDTOs.add(todo.toDTO());
        }
    
        return todoDTOs;
    }

    public ToDoDTO createToDo(ToDo toDo) throws ParseException {
        String id = java.util.UUID.randomUUID().toString();
        toDo.setId(id);
        toDo.setDone(false);
        toDo.setCreationDate(new Date());
        toDos.put(id, toDo);
        return toDo.toDTO();
    }

    public ToDoDTO getToDo(String id) throws ParseException {
        return toDos.get(id).toDTO();
    }

    public List<ToDoDTO> searchToDos(String name, String priority, Boolean isDone) throws ParseException {
        List<ToDoDTO> todoDTOs = new ArrayList<>();
        for (Entry<String, ToDo> entry : toDos.entrySet()) {
            ToDo currentToDo = entry.getValue();
            if (isMatchingFilter(currentToDo, name, priority, isDone)) {
                todoDTOs.add(currentToDo.toDTO());
            }
        }
        return todoDTOs;
    }
    
    private boolean isMatchingFilter(ToDo todo, String name, String priority, Boolean isDone) {
        if (name != null && !todo.getContent().contains(name)) {
            return false;
        }
    
        if (priority != null && todo.getPriority() != Priority.fromString(priority)) {
            return false;
        }
    
        if (isDone != null && isDone != todo.isDone()) {
            return false;
        }
    
        return true;
    }
    
}
