package com.bancvue.rest.exception;

import javax.ws.rs.core.Response;

public class SeeOtherException extends HttpClientException {

	private static final long serialVersionUID = -358816238969289521L;
	private String otherLocation;

	public SeeOtherException(String otherLocation) {
		super(otherLocation, Response.Status.SEE_OTHER);
		this.otherLocation = otherLocation;
	}

	public String getOtherLocation() {
		return otherLocation;
	}
}
