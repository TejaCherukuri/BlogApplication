package com.tejacodes.blogapplication.dto;

import java.util.Date;

public class ErrorResponse {

	private Date timestamp;
	private String message;
	private String api;
	
	public ErrorResponse(Date timestamp, String message, String api) {
		this.timestamp = timestamp;
		this.message = message;
		this.api = api;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getApi() {
		return api;
	}
	public void setApi(String api) {
		this.api = api;
	}
	
	
}
