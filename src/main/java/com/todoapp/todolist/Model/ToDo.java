package com.todoapp.todolist.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
//import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
//@EntityListeners(AuditingEntityListener.class)
public class ToDo {
    @Id
    private String id;

    private String content;
    private boolean isDone;
    private Date dueDate;
    private Priority priority;
    
    private Date doneDate;
    //@CreatedDate
    private Date creationDate;

    public ToDoDTO toDTO() throws ParseException {
        ToDoDTO dto = new ToDoDTO();
        dto.setId(getId());
        dto.setContent(getContent());
        dto.setDone(isDone());
        dto.setDueDate(getFormattedDate(getDueDate()));
        dto.setDoneDate(getFormattedDate(getDoneDate()));
        dto.setCreationDate(getFormattedDate(getCreationDate()));
        return dto;
    }

    private String getFormattedDate(Date date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        return date == null ? null : formatter.format(date);
    }
}