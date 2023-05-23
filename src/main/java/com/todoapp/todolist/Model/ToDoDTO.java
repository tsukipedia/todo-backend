package com.todoapp.todolist.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class ToDoDTO {
    @Id
    private String id;

    private String content;
    private boolean isDone;
    private String dueDate;
    private String priority;
    
    private String doneDate;
    //@CreatedDate
    private String creationDate;


    public ToDo toEntity() throws ParseException {
        ToDo toDo = new ToDo();
        toDo.setId(getId());
        toDo.setContent(getContent());
        toDo.setPriority(Priority.valueOf(getPriority()));
        toDo.setDone(isDone());
        toDo.setDueDate(getFormattedDate(getDueDate()));
        toDo.setDoneDate(getFormattedDate(getDoneDate()));
        return toDo;
    }

    private Date getFormattedDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        return date == null ? null : formatter.parse(date);
    }
}
