package com.iktpreobuka.project.controllers.utils;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.project.security.Views.Public;

public class RESTError {
	@JsonView(Public.class)
    private int statusCode;

    @JsonView(Public.class)
    private String message;

	public RESTError() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RESTError(int statusCode, String message) {
		super();
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
