package com.todoapp.todolist.Controller;

import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.todoapp.todolist.Model.Metrics;
import com.todoapp.todolist.Model.ToDoDTO;
import com.todoapp.todolist.Service.ToDoService;

@CrossOrigin
@RestController
@RequestMapping("/todo")
public class ToDoController {
    @Autowired
    ToDoService service;

    @GetMapping()
    public ResponseEntity<List<ToDoDTO>> getToDos(
            @RequestParam Integer pageSize,
            @RequestParam Integer lastFetchedIndex,
            @RequestParam(required = false) String sortBy) throws ParseException {
        return service.getToDos(pageSize, lastFetchedIndex, sortBy)
                .map(todos -> new ResponseEntity<>(todos, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ToDoDTO>> filterToDos(
            @RequestParam Integer pageSize,
            @RequestParam Integer lastFetchedIndex,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) Boolean isDone) throws ParseException {
        return service.searchToDos(pageSize, lastFetchedIndex, name, priority, isDone)
                .map(todos -> new ResponseEntity<List<ToDoDTO>>(todos, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping("/metrics")
    public ResponseEntity<Metrics> getTimeMetrics() {
        return new ResponseEntity<>(service.getTimeMetrics(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ToDoDTO> addToDo(@RequestBody ToDoDTO toDo) throws ParseException {
        try {
            return service.createToDo(toDo.toEntity())
                    .map(todo -> new ResponseEntity<ToDoDTO>(todo, HttpStatus.CREATED))
                    .orElseThrow(RuntimeException::new);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @PatchMapping("/{todoID}")
    public ResponseEntity<ToDoDTO> editToDo(@PathVariable("todoID") String toDoID, @RequestBody ToDoDTO toDo)
            throws ParseException {
        try {
            return new ResponseEntity<ToDoDTO>(service.editToDo(toDoID, toDo.toEntity()).get(), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping("/check/{todoID}")
    public ResponseEntity<ToDoDTO> checkToDo(@PathVariable("todoID") String toDoID) throws ParseException {
        try {
            return new ResponseEntity<ToDoDTO>(service.checkToDo(toDoID).get(), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{todoID}")
    public void deleteToDo(@PathVariable("todoID") String toDoID) {
        try {
            service.deleteToDo(toDoID);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
