package com.tejacodes.blogapplication.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CommentDTO {
	
	private long id;
	
	@NotEmpty(message = "Name should not be null or empty")
	private String name;
	
	@NotEmpty
	@Email
	private String email;
	
	@Size(min = 10, message = "Comment Body should have atleast 10 characters")
	private String body;
	
	public CommentDTO() {}

	public CommentDTO(long id, String name, String email, String body) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.body = body;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
}
