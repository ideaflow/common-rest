package com.bancvue.rest.exception;

import com.sun.jersey.api.client.ClientResponse;

public class ValidationException extends HttpClientException {

	private static final long serialVersionUID = -4362834942840542989L;

	public ValidationException() {
		super("Validation Error", ClientResponse.Status.BAD_REQUEST);
	}

	public ValidationException(String message) {
		super(message, ClientResponse.Status.BAD_REQUEST);
	}

}
