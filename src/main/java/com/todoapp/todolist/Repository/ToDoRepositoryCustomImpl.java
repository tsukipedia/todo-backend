package com.todoapp.todolist.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.todoapp.todolist.Model.Priority;
import com.todoapp.todolist.Model.ToDo;

@Service
public class ToDoRepositoryCustomImpl implements ToDoRepository {

    private static List<ToDo> toDos = new ArrayList<>();

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

        toDos.add(toDo1);
        toDos.add(toDo2);
    }

    public ToDo getToDo(String id) {
        return toDos.stream().filter(todo -> id.equals(todo.getId())).findFirst().orElse(null);
    }

    @Override
    public List<ToDo> findAll() {
        return toDos;
    }

    @Override
    public List<ToDo> findAllAndSortByDueDate() {
        List<ToDo> temp = toDos;
        temp.sort((todo1, todo2) -> {
            if (todo1.getDueDate() == null && todo2.getDueDate() == null) return 0;
            else if (todo1.getDueDate() == null) return -1;
            else if (todo2.getDueDate() == null) return 1;
            else return todo1.getDueDate().compareTo(todo2.getDueDate());
        });
        return temp;
    }

    @Override
    public List<ToDo> findAllAndSortByPriority() {
        List<ToDo> temp = toDos;
        temp.sort((todo1, todo2) -> {
            if (todo1.getPriority() == null && todo2.getPriority() == null) return 0;
            else if (todo1.getPriority() == null) return -1;
            else if (todo2.getPriority() == null) return 1;
            return todo1.getPriority().compareTo(todo2.getPriority());
        });
        return temp;
    }

    @Override
    public ToDo addToDo(ToDo todo) {
        String id = java.util.UUID.randomUUID().toString();
        todo.setId(id);
        todo.setDone(false);
        todo.setCreationDate(new Date());
        toDos.add(todo);
        return getToDo(id);
    }

    @Override
    public ToDo editToDo(String id, ToDo editEntity) {
        ToDo updatedToDo = getToDo(id);
        int index = toDos.indexOf(updatedToDo);
        if (editEntity.getContent() != updatedToDo.getContent()) updatedToDo.setContent(editEntity.getContent());
        if (editEntity.getDueDate() != updatedToDo.getDueDate()) updatedToDo.setDueDate(editEntity.getDueDate());
        if (editEntity.getPriority() != updatedToDo.getPriority()) updatedToDo.setPriority(editEntity.getPriority());
        toDos.set(index, updatedToDo);
        return getToDo(id);
    }

    @Override
    public List<ToDo> filterToDos(String name, Priority priority, Boolean isDone) {
        List<ToDo> todos = new ArrayList<>();
        for (ToDo todo : toDos) {
            if (isMatchingFilter(todo, name, priority, isDone)) {
                todos.add(todo);
            }
        }
        return todos;
    }

    @Override
    public ToDo changeToDoStatus(String id) {
        ToDo todo = getToDo(id);
        int index = toDos.indexOf(todo);
        if (todo.isDone()) {
            todo.setDone(false);
            todo.setDoneDate(null);
        }
        else {
            todo.setDone(true);
            todo.setDoneDate(new Date());
        }
        toDos.set(index, todo);
        return getToDo(id);
    }

    private boolean isMatchingFilter(ToDo todo, String name, Priority priority, Boolean isDone) {
        if (name != null && !todo.getContent().contains(name)) {
            return false;
        }
    
        if (priority != null && todo.getPriority() != priority) {
            return false;
        }
    
        if (isDone != null && isDone != todo.isDone()) {
            return false;
        }
    
        return true;
    }
    
}
