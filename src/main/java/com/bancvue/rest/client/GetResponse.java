package com.bancvue.rest.client;

import com.bancvue.rest.exception.HttpClientException;
import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.http.HttpStatus;

public class GetResponse {

	private ClientResponse response;
	private UnexpectedResponseExceptionFactory exceptionFactory;

	public GetResponse(ClientResponse response, UnexpectedResponseExceptionFactory exceptionFactory) {
		this.response = response;
		this.exceptionFactory = exceptionFactory;
	}

	public <T> T acquireResponseAsType(Class<T> type) {
		T typedResponse = getResponseAsType(type);
		if (typedResponse == null) {
			throw new HttpClientException("No entity found for location=" + response.getLocation(), ClientResponse.Status.NOT_FOUND);
		}
		return typedResponse;
	}

	public <T> T getResponseAsType(Class<T> type) {
		try {
			return doGetResponseAsType(type);
		} finally {
			response.close();
		}
	}

	private <T> T doGetResponseAsType(Class<T> type) {
		if (response.getStatus() == HttpStatus.SC_OK) {
			return response.getEntity(type);
		} else if (response.getStatus() == HttpStatus.SC_NOT_FOUND) {
			return null;
		} else {
			throw exceptionFactory.createException(response);
		}
	}

}
