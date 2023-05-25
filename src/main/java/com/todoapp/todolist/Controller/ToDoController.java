package com.todoapp.todolist.Controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todoapp.todolist.Model.ToDoDTO;
import com.todoapp.todolist.Service.ToDoService;

@RestController
@RequestMapping("/todo")
public class ToDoController {
    @Autowired
    ToDoService service;

    @GetMapping()
    public ResponseEntity<List<ToDoDTO>> getToDos(
        @RequestParam(required = false) String sortBy) throws ParseException {
        return service.getToDos(sortBy).map(todos -> new ResponseEntity<>(todos, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ToDoDTO>> filterToDos(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) Boolean isDone) throws ParseException {
        return service.searchToDos(name, priority, isDone)
                .map(todos -> new ResponseEntity<List<ToDoDTO>>(todos, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping()
    public ResponseEntity<ToDoDTO> addToDo(@RequestBody ToDoDTO toDo) throws ParseException {
        return service.createToDo(toDo.toEntity()).map(todo -> new ResponseEntity<ToDoDTO>(todo, HttpStatus.OK))
                .orElseThrow(RuntimeException::new);
    }

    @PatchMapping("/{todoID}")
    public ResponseEntity<ToDoDTO> editToDo(@PathVariable("todoID") String toDoID, @RequestBody ToDoDTO toDo)
            throws ParseException {
        return new ResponseEntity<ToDoDTO>(service.editToDo(toDoID, toDo.toEntity()).get(), HttpStatus.OK);
    }

}
