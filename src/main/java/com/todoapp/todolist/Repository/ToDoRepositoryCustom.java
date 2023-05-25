package com.todoapp.todolist.Repository;

import java.util.List;

import com.todoapp.todolist.Model.ToDo;
import com.todoapp.todolist.Model.Priority;

public interface ToDoRepositoryCustom {
    public List<ToDo> findAll();
    public List<ToDo> findAllAndSortByDueDate();
    public List<ToDo> findAllAndSortByPriority();
    public ToDo addToDo(ToDo todo);
    public ToDo editToDo(String id, ToDo todo);
    public List<ToDo> filterToDos(String name, Priority priority, Boolean isDone);
    public ToDo changeToDoStatus(String id);
}
