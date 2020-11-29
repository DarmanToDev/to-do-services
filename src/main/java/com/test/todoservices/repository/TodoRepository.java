package com.test.todoservices.repository;

import java.util.List;
import java.util.Optional;

import com.test.todoservices.model.Todo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    abstract Optional<Todo> findById(Integer id);
    abstract List<Todo> findAll();
}
