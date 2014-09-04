package com.bancvue.rest.exception;

import javax.ws.rs.WebApplicationException;
import org.apache.http.HttpStatus;

public class ValidationException extends WebApplicationException {

	public ValidationException() {
		this("Validation Error");
	}

	public ValidationException(String message) {
		super(message, HttpStatus.SC_UNPROCESSABLE_ENTITY);
	}

}
