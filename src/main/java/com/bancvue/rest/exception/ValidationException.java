package com.bancvue.rest.exception;

import javax.ws.rs.core.Response;

public class ValidationException extends HttpClientException {

	private static final long serialVersionUID = -4362834942840542989L;

	public ValidationException() {
		super("Validation Error", Response.Status.BAD_REQUEST);
	}

	public ValidationException(String message) {
		super(message, Response.Status.BAD_REQUEST);
	}

}
