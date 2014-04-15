package com.bancvue.rest.exception;

import javax.ws.rs.core.Response;

public class NotFoundException extends HttpClientException {

	private static final long serialVersionUID = -7025040484134678764L;

	public NotFoundException(String msg) {
		super(msg, Response.Status.NOT_FOUND);
	}

}
