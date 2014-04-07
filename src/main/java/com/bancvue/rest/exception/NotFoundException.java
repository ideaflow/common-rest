package com.bancvue.rest.exception;

import com.sun.jersey.api.client.ClientResponse;

public class NotFoundException extends HttpClientException {

	private static final long serialVersionUID = -7025040484134678764L;

	public NotFoundException(String msg) {
		super(msg, ClientResponse.Status.NOT_FOUND);
	}

}
