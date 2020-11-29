package com.test.todoservices.repository;

import com.test.todoservices.model.Tag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    
}
