package com.todoapp.todolist.Repository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.todoapp.todolist.Model.Metrics;
import com.todoapp.todolist.Model.Priority;
import com.todoapp.todolist.Model.ToDo;

import jakarta.annotation.PostConstruct;

@Service
public class ToDoRepositoryCustomImpl implements ToDoRepository {

    private static List<ToDo> toDos = new ArrayList<>();

    @PostConstruct
    public void init() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            ToDo dummyToDo = new ToDo();
            dummyToDo.setContent("test item " + i);
            dummyToDo.setPriority(Priority.values()[random.nextInt(Priority.values().length)]);

            // Create random dates within the last year for creationDate
            LocalDate localDate = LocalDate.now().minusDays(random.nextInt(365));
            Date creationDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            dummyToDo.setCreationDate(creationDate);

            // Create random dates within the next year for dueDate
            localDate = LocalDate.now().plusDays(random.nextInt(365));
            Date dueDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            dummyToDo.setDueDate(dueDate);

            boolean isDone = random.nextBoolean();
            dummyToDo.setDone(isDone);

            if (isDone) {
                long elapsedDaysBetween = ThreadLocalRandom.current().nextLong(1, 365);
                Date doneDate = Date.from(dummyToDo.getCreationDate().toInstant().plus(Duration.ofDays(elapsedDaysBetween)));
                dummyToDo.setDoneDate(doneDate);
            } else {
                dummyToDo.setDoneDate(null);
            }

            addToDo(dummyToDo);
        }
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
        //todo.setDone(false);
        //todo.setCreationDate(new Date());
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

    @Override
    public Metrics calculateMetrics() {
        Metrics metrics = new Metrics();
        metrics.setAverageTime(calculateAverageTime(toDos, null));
        metrics.setLowPriorityAverageTime(calculateAverageTime(toDos, Priority.LOW));
        metrics.setMediumPriorityAverageTime(calculateAverageTime(toDos, Priority.MEDIUM));
        metrics.setHighPriorityAverageTime(calculateAverageTime(toDos, Priority.HIGH));
        return metrics;
    }
    
    private String calculateAverageTime(List<ToDo> toDos, Priority priority) {
        OptionalDouble averageTimeOptional = toDos.stream()
                .filter(todo -> todo.isDone() && todo.getDoneDate() != null && (priority == null || todo.getPriority() == priority))
                .mapToLong(todo -> todo.getDoneDate().getTime() - todo.getCreationDate().getTime())
                .average();
        return averageTimeOptional.isPresent() ? getTimeString((long) averageTimeOptional.getAsDouble()) : "No data available";
    }
    
    private String getTimeString(long milliseconds) {
        Duration duration = Duration.ofMillis(milliseconds);
        long weeks = duration.toDays() / 7;
        long days = duration.toDays() % 7;
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        return (weeks + " weeks, " + days + " days, " + hours + " hours, " + minutes + " minutes, " + seconds + " seconds");
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
