package com.bancvue.rest.client;

import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.http.HttpStatus;

public class UpdateResponse {

	private ClientResponse response;
	private UnexpectedResponseExceptionFactory exceptionFactory;

	public UpdateResponse(ClientResponse response, UnexpectedResponseExceptionFactory exceptionFactory) {
		this.response = response;
		this.exceptionFactory = exceptionFactory;
	}

	public void assertResponseSuccess() {
		try {
			doAssertResponseSuccess();
		} finally {
			response.close();
		}
	}

	private void doAssertResponseSuccess() {
		if (response.getStatus() != HttpStatus.SC_NO_CONTENT) {
			throw exceptionFactory.createException(response);
		}
	}
}
