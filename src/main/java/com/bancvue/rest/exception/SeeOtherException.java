package com.bancvue.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class SeeOtherException extends WebApplicationException {

	private String otherLocation;

	public SeeOtherException(String otherLocation) {
		super(otherLocation, Response.Status.SEE_OTHER);
		this.otherLocation = otherLocation;
	}

	public String getOtherLocation() {
		return otherLocation;
	}
}
