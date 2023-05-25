package com.todoapp.todolist.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoapp.todolist.Model.Priority;
import com.todoapp.todolist.Model.ToDo;
import com.todoapp.todolist.Model.ToDoDTO;
import com.todoapp.todolist.Repository.ToDoRepository;

@Service
public class ToDoService {

    @Autowired
    ToDoRepository repository;

    public Optional<List<ToDoDTO>> getToDos(String sortBy) throws ParseException {
        List<ToDoDTO> todoDTOs = new ArrayList<>();
        List<ToDo> todos = new ArrayList<>();

        if(sortBy == null) todos = repository.findAll();
        else {
            if(sortBy.equalsIgnoreCase("priority")) {
                todos = repository.findAllAndSortByPriority();
            }
            else if(sortBy.equalsIgnoreCase("due date") || sortBy.equalsIgnoreCase("duedate")) {
                todos = repository.findAllAndSortByDueDate();
            }
            else {
                todos = repository.findAll();
            }
        }

        for (ToDo todo : todos) {
            todoDTOs.add(todo.toDTO());
        }

        return Optional.ofNullable(todoDTOs);
    }

    public Optional<ToDoDTO> createToDo(ToDo toDo) throws ParseException {
        return Optional.of(repository.addToDo(toDo).toDTO());
    }

    public Optional<List<ToDoDTO>> searchToDos(String name, String priority, Boolean isDone) throws ParseException {
        List<ToDoDTO> todoDTOs = new ArrayList<>();
        List<ToDo> filteredToDos = repository.filterToDos(name, Priority.fromString(priority), isDone);

        for (ToDo todo : filteredToDos) {
            todoDTOs.add(todo.toDTO());
        }

        return Optional.of(todoDTOs);
    }

    public Optional<ToDoDTO> editToDo(String id, ToDo editEntity) throws ParseException {
        return Optional.of(repository.editToDo(id, editEntity).toDTO());
    }
    
}
