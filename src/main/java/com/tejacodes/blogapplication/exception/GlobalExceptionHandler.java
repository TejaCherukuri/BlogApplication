package com.tejacodes.blogapplication.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tejacodes.blogapplication.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
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
	
	// This method is used to handle JSR 380 Validation (Hibernate Validator)
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Map<String,String> errors = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	
	//The below way is the one more way to achieve handleMethodArgumentNotValid without overriding ResponseEntityExceptionHandler

//  @ExceptionHandler(MethodArgumentNotValidException.class)
//  public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
//                                                                      WebRequest webRequest){
//      Map<String, String> errors = new HashMap<>();
//      exception.getBindingResult().getAllErrors().forEach((error) ->{
//          String fieldName = ((FieldError)error).getField();
//          String message = error.getDefaultMessage();
//          errors.put(fieldName, message);
//      });
//      return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//  }
	
}
