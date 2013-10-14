package com.bancvue.rest.client;

import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.http.HttpStatus;

public class PutResponse {

	private ClientResponse clientResponse;
	private UnexpectedResponseExceptionFactory exceptionFactory;

	public PutResponse(ClientResponse clientResponse, UnexpectedResponseExceptionFactory exceptionFactory) {
		this.clientResponse = clientResponse;
		this.exceptionFactory = exceptionFactory;
	}

	public void assertResponseSuccess() {
		try {
			doAssertResponseSuccess();
		} finally {
			clientResponse.close();
		}
	}

	private void doAssertResponseSuccess() {
		if (clientResponse.getStatus() != HttpStatus.SC_NO_CONTENT) {
			throw exceptionFactory.createException(clientResponse);
		}
	}
}
