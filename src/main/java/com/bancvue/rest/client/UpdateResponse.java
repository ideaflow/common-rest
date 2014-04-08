package com.bancvue.rest.client;

import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory;
import com.bancvue.rest.exception.ValidationException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

import org.apache.http.HttpStatus;

public class UpdateResponse {

	private ClientResponse clientResponse;
	private UnexpectedResponseExceptionFactory exceptionFactory;

	public UpdateResponse(ClientResponse clientResponse, UnexpectedResponseExceptionFactory exceptionFactory) {
		this.clientResponse = clientResponse;
		this.exceptionFactory = exceptionFactory;
	}

	public <T> T assertEntityUpdatedAndGetResponse(Class<T> type) {
		return assertEntityUpdatedAndGetResponse(type, EntityResolver.CLASS_RESOLVER);
	}

	public <T> T assertEntityUpdatedAndGetResponse(GenericType<T> type) {
		return assertEntityUpdatedAndGetResponse(type, EntityResolver.GENERIC_TYPE_RESOLVER);
	}

	private <T> T assertEntityUpdatedAndGetResponse(Object typeOrGenericType, EntityResolver resolver) {
		try {
			return doAssertEntityUpdatedAndGetResponse(typeOrGenericType, resolver);
		} finally {
			clientResponse.close();
		}
	}

	private <T> T doAssertEntityUpdatedAndGetResponse(Object typeOrGenericType, EntityResolver resolver) {
		if (clientResponse.getStatus() == HttpStatus.SC_BAD_REQUEST) {
			String msg = EntityResolver.CLASS_RESOLVER.getEntity(clientResponse, String.class);
			throw new ValidationException(msg);
		} else if (clientResponse.getStatus() == HttpStatus.SC_OK) {
			return resolver.getEntity(clientResponse, typeOrGenericType);
		}
		throw exceptionFactory.createException(clientResponse);
	}
}
