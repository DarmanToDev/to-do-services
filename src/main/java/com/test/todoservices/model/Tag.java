package com.test.todoservices.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

@Entity 
@Table(name = "tags") 
@Where(clause = "is_active = 1")
public class Tag {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "id")
    @Setter @Getter private Integer id;
	
	@Column(name = "name")
	@NotNull
    @Setter @Getter private String name;
	
	@Column(name = "is_active")
	@NotNull
	@Setter @Getter private Boolean isActive = true;
	
	@Column(name = "created_at") 
	@NotNull @CreationTimestamp
	@Setter @Getter private Timestamp createdAt;
	
	@Column(name = "updated_at") 
	@NotNull @UpdateTimestamp
	@Setter @Getter private Timestamp updatedAt;
	
}
