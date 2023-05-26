package com.todoapp.todolist.Repository;

import java.util.List;

import com.todoapp.todolist.Model.ToDo;
import com.todoapp.todolist.Model.Metrics;
import com.todoapp.todolist.Model.Priority;

public interface ToDoRepositoryCustom {
    public List<ToDo> findAll(Integer pageSize, Integer lastFetchedIndex);
    public List<ToDo> findAllAndSortByDueDate(Integer pageSize, Integer lastFetchedIndex);
    public List<ToDo> findAllAndSortByPriority(Integer pageSize, Integer lastFetchedIndex);
    public ToDo addToDo(ToDo todo);
    public ToDo editToDo(String id, ToDo todo);
    public List<ToDo> filterToDos(Integer pageSize, Integer lastFetchedIndex, String name, Priority priority, Boolean isDone);
    public ToDo changeToDoStatus(String id);
    public Metrics calculateMetrics();
    public void deleteToDo(String id);
}
