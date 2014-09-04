package com.bancvue.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ConflictException extends WebApplicationException {

	public ConflictException(String message) {
		super(message, Response.Status.CONFLICT);
	}

}
