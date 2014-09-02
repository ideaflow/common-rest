package com.bancvue.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class NotFoundException extends WebApplicationException {

	public NotFoundException(String msg) {
		super(msg, Response.Status.NOT_FOUND);
	}

}
