package com.bancvue.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ConflictingEntityException extends WebApplicationException {

	private Object entity;

	public ConflictingEntityException(String message, Object entity) {
		super(message, Response.Status.CONFLICT);
		this.entity = entity;
	}

	@SuppressWarnings("unchecked")
	public <T> T getEntity() {
		return (T) entity;
	}

}
