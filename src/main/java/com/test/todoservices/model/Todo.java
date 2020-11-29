package com.test.todoservices.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "todos") 
@Where(clause = "is_active = 1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	@NotNull
    private String name;
	
	@Column(name = "is_finished")
	private Boolean isFinished = false;

	@Column(name = "is_active")
	private Boolean isActive = true;
	
	@Column(name = "created_at") 
	@CreationTimestamp
	private Timestamp createdAt;
	
	@Column(name = "updated_at") 
	@UpdateTimestamp
	private Timestamp updatedAt;

	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
		name = "todos_tags",
		joinColumns = @JoinColumn(name = "todo_id"),
		inverseJoinColumns = @JoinColumn(name = "tags_id")
    )
    private Set<Tag> tags = new HashSet<>();

}
