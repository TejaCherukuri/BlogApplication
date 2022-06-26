package com.tejacodes.blogapplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String resource;
	private String fieldName;
	private Long fieldValue;
	
	public ResourceNotFoundException() {}
	
	public ResourceNotFoundException(String resource, String fieldName, Long fieldValue) {
		super(String.format("%s not found with %s : '%s'", resource, fieldName, fieldValue));
		this.resource = resource;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public String getResource() {
		return resource;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Long getFieldValue() {
		return fieldValue;
	}


}
