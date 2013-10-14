package com.bancvue.rest.client;

import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import org.apache.http.HttpStatus;

public class PutResponse {

	private ClientResponse clientResponse;
	private UnexpectedResponseExceptionFactory exceptionFactory;

	public PutResponse(ClientResponse clientResponse, UnexpectedResponseExceptionFactory exceptionFactory) {
		this.clientResponse = clientResponse;
		this.exceptionFactory = exceptionFactory;
	}

	public <T> T assertEntityUpdatedAndGetEntity(Class<T> type) {
		return assertEntityUpdatedAndGetEntity(type, EntityResolver.CLASS_RESOLVER);
	}

	public <T> T assertEntityUpdatedAndGetEntity(GenericType<T> type) {
		return assertEntityUpdatedAndGetEntity(type, EntityResolver.GENERIC_TYPE_RESOLVER);
	}

	private <T> T assertEntityUpdatedAndGetEntity(Object typeOrGenericType, EntityResolver resolver) {
		try {
			return doAssertEntityUpdatedAndGetEntity(typeOrGenericType, resolver);
		} finally {
			clientResponse.close();
		}
	}

	private <T> T doAssertEntityUpdatedAndGetEntity(Object typeOrGenericType, EntityResolver resolver) {
		if (clientResponse.getStatus() == HttpStatus.SC_OK) {
			return resolver.getEntity(clientResponse, typeOrGenericType);
		}
		throw exceptionFactory.createException(clientResponse);
	}

}
