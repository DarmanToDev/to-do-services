package com.test.todoservices.controller;

import java.beans.FeatureDescriptor;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.todoservices.dto.TodoRequest;
import com.test.todoservices.exception.ResourceNotFoundException;
import com.test.todoservices.model.Todo;
import com.test.todoservices.repository.TagRepository;
import com.test.todoservices.repository.TodoRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
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
    
    @Autowired
	private TagRepository tagRepository;

	@CrossOrigin(origins = "*")
    @RequestMapping(value="/app/todo", method = RequestMethod.POST)
    public ResponseEntity<?> addTodo(@Valid @RequestBody TodoRequest todoReq) throws Exception {
          
        Todo newTodo = new Todo();
        BeanUtils.copyProperties(todoReq, newTodo); 
        todoReq.getTags().stream().forEach(tag -> this.tagRepository.save(tag));
	    this.todoRepository.save(newTodo);
            
        return ResponseEntity.status(HttpStatus.CREATED).body(newTodo);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/app/todo/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> editTodo(@PathVariable Integer id, @Valid @RequestBody TodoRequest todoReq) throws Exception {
       
        Todo existTodo = this.todoRepository.findById(id).orElse(null);
        if(existTodo == null){
            throw new ResourceNotFoundException();
        } else {
            BeanUtils.copyProperties(todoReq, existTodo, getNullPropertyNames(todoReq));
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
            return ResponseEntity.status(HttpStatus.OK).body(todo);
        }
    }
  
    @CrossOrigin(origins = "*")
    @RequestMapping(value="/app/todo/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTodo(@PathVariable Integer id) throws Exception {

        Todo existTodo = this.todoRepository.findById(id).orElse(null);
        if(existTodo == null){
            throw new ResourceNotFoundException();
        } else {
            existTodo.setIsActive(false);
            this.todoRepository.save(existTodo);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
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

    private TodoRequest convertToDto(Todo todo) {
        TodoRequest todoResponse = MAPPER.convertValue(todo, TodoRequest.class);
        return todoResponse;
    }

    public static String[] getNullPropertyNames(Object source) {
	    final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
	    return Stream.of(wrappedSource.getPropertyDescriptors())
	        .map(FeatureDescriptor::getName)
	        .filter(propertyName -> {
	            try {
	               return wrappedSource.getPropertyValue(propertyName) == null;
	            } catch (Exception e) {
	               return false;
	            }                
	        })
	        .toArray(String[]::new);
    }
    
}
