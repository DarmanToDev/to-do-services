package com.test.todoservices.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.todoservices.dto.TodoRequest;
import com.test.todoservices.exception.ResourceNotFoundException;
import com.test.todoservices.model.Todo;
import com.test.todoservices.repository.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Autowired
	private TodoRepository todoRepository;

	@CrossOrigin(origins = "*")
    @RequestMapping(value="/app/todo", method = RequestMethod.POST)
    public ResponseEntity<?> addTodo(@Valid @RequestBody TodoRequest todoReq) throws Exception {
          
        Todo todo = MAPPER.convertValue(todoReq, Todo.class); 
	    this.todoRepository.save(todo);
            
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(todo));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/app/todo/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> editTodo(@PathVariable Integer id) throws Exception {
       
        Todo existTodo = this.todoRepository.findById(id).orElse(null);
        if(existTodo == null){
            throw new ResourceNotFoundException();
        } else {
            // existTodo = MAPPER.convertValue(todoReq, Todo.class);
            this.todoRepository.save(existTodo);

            return ResponseEntity.status(HttpStatus.OK).body(convertToDto(existTodo));
        }
    }

	@CrossOrigin(origins = "*")
    @RequestMapping(value="/app/todo/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> todoDetail(@PathVariable Integer id) throws Exception {
         
        Todo todo = this.todoRepository.findById(id).orElse(null);
        if(todo == null){
            throw new ResourceNotFoundException();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(convertToDto(todo));
        }
    }
  
    @CrossOrigin(origins = "*")
    @RequestMapping(value="/app/todo/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTodo(@PathVariable Integer id) throws Exception {

            Todo existTodo = this.todoRepository.findById(id).orElse(null);
            if(existTodo == null){
                throw new ResourceNotFoundException();
            } else {
                // existTodo.setIsActive(false);
                this.todoRepository.save(existTodo);

                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
    }

    private TodoRequest convertToDto(Todo todo) {
        // ModelMapper modelMapper = new ModelMapper();
        // TodoRequest todoResponse = modelMapper.map(todo, TodoRequest.class);
        TodoRequest todoResponse = MAPPER.convertValue(todo, TodoRequest.class);
        return todoResponse;
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value="/app/todos", method = RequestMethod.GET)
    public ResponseEntity<?> listTodo() throws Exception {
      
        List<Todo> listTodo = this.todoRepository.findAll();
        if(listTodo != null){
            
            return ResponseEntity.status(HttpStatus.OK).body(listTodo.stream().map(this::convertToDto).collect(Collectors.toList()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new String("{}"));
    }

}
