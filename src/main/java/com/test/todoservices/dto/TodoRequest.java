package com.test.todoservices.dto;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.test.todoservices.model.Tag;

import lombok.Data;

@Data
public class TodoRequest {
	private Integer id;

	@JsonProperty("name") @NotNull
	private String name;

	@JsonProperty("is_finished")
	private Boolean isFinished;
	
	@JsonProperty("is_active")
	private Boolean isActive = true;
	
	@JsonProperty("created_at")
	private Timestamp createdAt;
	
	@JsonProperty("updated_at")
	private Timestamp updatedAt;

	@JsonProperty("tags")
	private Set<Tag> tags = new HashSet<>();

}
