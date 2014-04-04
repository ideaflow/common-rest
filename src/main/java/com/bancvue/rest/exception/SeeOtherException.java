package com.bancvue.rest.exception;

import com.sun.jersey.api.client.ClientResponse;

public class SeeOtherException extends HttpClientException {

	private static final long serialVersionUID = -358816238969289521L;
	private String otherLocation;

	public SeeOtherException(String otherLocation) {
		super(otherLocation, ClientResponse.Status.SEE_OTHER);
		this.otherLocation = otherLocation;
	}

	public String getDeleteLocation() {
		return otherLocation;
	}
}
