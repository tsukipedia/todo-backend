package com.todoapp.todolist.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

@Data
public class ToDo {
    private String id;

    private String content;
    private boolean isDone;
    private Date dueDate;
    private Priority priority;
    
    private Date doneDate;
    private Date creationDate;

    public ToDoDTO toDTO() throws ParseException {
        ToDoDTO dto = new ToDoDTO();
        dto.setId(getId());
        dto.setContent(getContent());
        dto.setDone(isDone());
        dto.setPriority(getPriority() != null ? getPriority().toString() : null);
        dto.setDueDate(getFormattedDate(getDueDate()));
        dto.setDoneDate(getFormattedDate(getDoneDate()));
        dto.setCreationDate(getFormattedDate(getCreationDate()));
        return dto;
    }

    private String getFormattedDate(Date date) throws ParseException {
        if(date == null) {
            return null;
        }
        else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            return formatter.format(date);
        }
    }
}