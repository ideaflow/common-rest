package com.bancvue.rest.exception;

import org.apache.http.HttpStatus;

public class ValidationException extends HttpClientException {

	private static final long serialVersionUID = -4362834942840542989L;

	public ValidationException() {
		this("Validation Error");
	}

	public ValidationException(String message) {
		super(message, HttpStatus.SC_UNPROCESSABLE_ENTITY);
	}

}
