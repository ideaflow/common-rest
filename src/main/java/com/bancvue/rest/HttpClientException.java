package com.bancvue.rest;

import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class HttpClientException extends WebApplicationException {

	public static HttpClientException unexpected(int statusCode) {
		return unexpected(Response.status(statusCode).build());
	}

	public static HttpClientException unexpected(Response response) {
		return new HttpClientException("Unexpected status code=" + response.getStatus(), response);
	}


	private String message;

	public HttpClientException(String message, Response response) {
		super(response);
		this.message = message;
	}

	public HttpClientException(String message, ClientResponse.Status status) {
		this(message, status.getStatusCode());
	}

	public HttpClientException(String message, int statusCode) {
		this(message, Response.status(statusCode).build());
	}

	public int getStatus() {
		return getResponse().getStatus();
	}

	@Override
	public String getMessage() {
		return message;
	}

}
