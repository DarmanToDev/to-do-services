package com.test.todoservices.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

public class TodoRequest {
	
	@Setter @Getter 
	private Integer id;

	@Setter @Getter @NotNull
	private String name;

	@Setter @Getter @NotNull
	private Boolean isFinished;
	
	@Setter @Getter @NotNull
	private Boolean isActive;
	
	@Setter @Getter @NotNull
	private Timestamp createdAt;
	
	@Setter @Getter @NotNull
	private Timestamp updatedAt;

}
