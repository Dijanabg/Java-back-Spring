package com.iktpreobuka.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	    @ExceptionHandler(ResourceNotFoundException.class)
	    @ResponseStatus(HttpStatus.NOT_FOUND)
	    public ErrorMessage handleResourceNotFoundException(ResourceNotFoundException ex) {
	        return new ErrorMessage(HttpStatus.NOT_FOUND.value(), ex.getMessage());
	    }

	    // Klasa za formatiranje poruke o grešci
	    public static class ErrorMessage {
	        private int statusCode;
	        private String message;
	    
		    public ErrorMessage(int statusCode, String message) {
		        this.statusCode = statusCode;
		        this.message = message;
		    }
	
			public int getStatusCode() {
				return statusCode;
			}
	
			public void setStatusCode(int statusCode) {
				this.statusCode = statusCode;
			}
	
			public String getMessage() {
				return message;
			}
	
			public void setMessage(String message) {
				this.message = message;
			}
	    }
}
