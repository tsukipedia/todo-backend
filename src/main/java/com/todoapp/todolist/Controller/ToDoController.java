package com.todoapp.todolist.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todoapp.todolist.Model.ToDo;
import com.todoapp.todolist.Service.ToDoService;

@RestController
@RequestMapping("/todo")
public class ToDoController {
    @Autowired
    ToDoService service;

    @GetMapping()
    public ResponseEntity<List<ToDo>> getToDos() {
        return new ResponseEntity<List<ToDo>>(service.getToDos(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ToDo> addToDo(@RequestBody ToDo toDo) {
        return new ResponseEntity<ToDo>(service.createToDo(toDo), HttpStatus.OK);
    }
}
