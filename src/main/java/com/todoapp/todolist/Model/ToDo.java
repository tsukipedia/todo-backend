package com.todoapp.todolist.Model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ToDo {
    @Id
    private String id;

    private boolean isDone;
    private Date dueDate;
    private Priority priority;
    
    private Date doneDate;
    @CreatedDate
    private Date creationDate;
}