package com.bancvue.rest.client;

import com.bancvue.rest.exception.HttpClientException;
import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.http.HttpStatus;

public class CreateResponse {

	private ClientResponse response;
	private UnexpectedResponseExceptionFactory exceptionFactory;

	public CreateResponse(ClientResponse response, UnexpectedResponseExceptionFactory exceptionFactory) {
		this.response = response;
		this.exceptionFactory = exceptionFactory;
	}

	public <T> T assertEntityCreatedAndGet(Class<T> type) {
		try {
			return doAssertEntityCreatedAndGet(type);
		} finally {
			response.close();
		}
	}

	private <T> T doAssertEntityCreatedAndGet(Class<T> type) {
		if (response.getStatus() == HttpStatus.SC_CONFLICT) {
			throw new HttpClientException("Entity already exists", response.getStatus());
		} else if (response.getStatus() != HttpStatus.SC_CREATED) {
			throw exceptionFactory.createException(response);
		}
		return response.getEntity(type);
	}

}
