package com.todoapp.todolist.Controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todoapp.todolist.Model.ToDoDTO;
import com.todoapp.todolist.Service.ToDoService;

@RestController
@RequestMapping("/todo")
public class ToDoController {
    @Autowired
    ToDoService service;

    @GetMapping()
    public ResponseEntity<List<ToDoDTO>> getToDos() throws ParseException {
        return new ResponseEntity<List<ToDoDTO>>(service.getToDos(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ToDoDTO> addToDo(@RequestBody ToDoDTO toDo) throws ParseException {
        return new ResponseEntity<ToDoDTO>(service.createToDo(toDo.toEntity()), HttpStatus.OK);
    }

}
