package com.test.todoservices.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "tags") 
@Where(clause = "is_active = 1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "id")
    private Integer id;
	
	@Column(name = "name")
	@JsonProperty("name") @NotNull
    private String name;
	
	@Column(name = "is_active")
	@JsonProperty("is_active")
	private Boolean isActive = true;
	
	@Column(name = "created_at") 
	@JsonProperty("created_at") @CreationTimestamp
	private Timestamp createdAt;
	
	@Column(name = "updated_at") 
	@JsonProperty("updated_at") @UpdateTimestamp
	private Timestamp updatedAt;
	
}
