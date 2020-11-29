package com.test.todoservices.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TodoRequest {
	private Integer id;

	@JsonProperty("name") @NotNull
	private String name;

	@JsonProperty("is_finished") @NotNull
	private Boolean isFinished;
	
	@JsonProperty("is_active")
	private Boolean isActive;
	
	@JsonProperty("created_at")
	private Timestamp createdAt;
	
	@JsonProperty("updated_at")
	private Timestamp updatedAt;

}
