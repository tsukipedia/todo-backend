package com.todoapp.todolist.Model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String content;
    private boolean isDone;
    private Date dueDate;
    private Priority priority;
    
    private Date doneDate;
    @CreatedDate
    private Date creationDate;
}