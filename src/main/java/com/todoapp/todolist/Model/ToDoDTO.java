package com.todoapp.todolist.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

@Data
public class ToDoDTO {
    private String id;

    private String content;
    private boolean isDone;
    private String dueDate;
    private String priority;
    
    private String doneDate;
    private String creationDate;

    public ToDo toEntity() throws ParseException {
        ToDo toDo = new ToDo();
        toDo.setId(getId());
        toDo.setContent(getContent());
        toDo.setPriority(Priority.fromString(getPriority()));
        toDo.setDone(isDone());
        toDo.setDueDate(getFormattedDate(getDueDate()));
        toDo.setDoneDate(getFormattedDate(getDoneDate()));
        return toDo;
    }

    private Date getFormattedDate(String date) throws ParseException {
        if(date == null) {
            return null;
        }
        else {
            date = date.replace(' ', 'T');
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
            return formatter.parse(date);
        }
    }
}
