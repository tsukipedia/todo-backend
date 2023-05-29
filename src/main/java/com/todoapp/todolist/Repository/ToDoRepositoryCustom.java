package com.todoapp.todolist.Repository;

import com.todoapp.todolist.Model.ToDo;
import com.todoapp.todolist.Model.ToDoList;
import com.todoapp.todolist.Model.Metrics;
import com.todoapp.todolist.Model.Priority;

public interface ToDoRepositoryCustom {
    public ToDoList findAll(Integer pageSize, Integer lastFetchedIndex);
    public ToDoList findAllAndSortByDueDate(Integer pageSize, Integer lastFetchedIndex);
    public ToDoList findAllAndSortByPriority(Integer pageSize, Integer lastFetchedIndex);
    public ToDo addToDo(ToDo todo);
    public ToDo editToDo(String id, ToDo todo);
    public ToDoList filterToDos(Integer pageSize, Integer lastFetchedIndex, String name, Priority priority, Boolean isDone);
    public ToDo changeToDoStatus(String id);
    public Metrics calculateMetrics();
    public void deleteToDo(String id);
}
