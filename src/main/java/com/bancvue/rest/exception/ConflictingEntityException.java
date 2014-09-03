package com.bancvue.rest.exception;

import javax.ws.rs.core.Response;

public class ConflictingEntityException extends HttpClientException {

	private Object entity;

	public ConflictingEntityException(String message, Object entity) {
		super(message, Response.Status.CONFLICT);
		this.entity = entity;
	}

	public <T> T getEntity() {
		return (T) entity;
	}

}
