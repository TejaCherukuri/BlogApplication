package com.tejacodes.blogapplication.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.tejacodes.blogapplication.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	//Handle specific Exceptions
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest webRequest)
	{
		ErrorResponse errorResponse = new ErrorResponse(new Date(), e.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BlogApiException.class)
	public ResponseEntity<ErrorResponse> handleBlogApiException(BlogApiException e, WebRequest webRequest)
	{
		ErrorResponse errorResponse = new ErrorResponse(new Date(), e.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	//Global Exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGlobalException(Exception e, WebRequest webRequest)
	{
		ErrorResponse errorResponse = new ErrorResponse(new Date(), e.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
