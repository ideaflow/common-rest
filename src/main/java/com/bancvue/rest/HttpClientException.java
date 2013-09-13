package com.bancvue.rest;

import org.apache.commons.lang3.reflect.FieldUtils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class HttpClientException extends WebApplicationException {

    public static HttpClientException unexpected(int statusCode) {
	    return unexpected(Response.status(statusCode).build());
    }

	public static HttpClientException unexpected(Response response) {
		return new HttpClientException("Unexpected status code=" + response.getStatus(), response);
	}


	public HttpClientException(String message, Response response) {
		super(response);
		try {
			FieldUtils.writeField(this, "detailMessage", message, true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

    public HttpClientException(String message, int statusCode) {
	    this(message, Response.status(statusCode).build());
    }

	public int getStatus() {
		return getResponse().getStatus();
	}

}
