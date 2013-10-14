package com.bancvue.rest.client;

import com.bancvue.rest.exception.HttpClientException;
import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import org.apache.http.HttpStatus;

public class PostResponse {

	private ClientResponse clientResponse;
	private UnexpectedResponseExceptionFactory exceptionFactory;

	public PostResponse(ClientResponse clientResponse, UnexpectedResponseExceptionFactory exceptionFactory) {
		this.clientResponse = clientResponse;
		this.exceptionFactory = exceptionFactory;
	}

	public <T> T assertEntityCreatedAndGet(Class<T> type) {
		return assertEntityCreatedAndGet(type, EntityResolver.CLASS_RESOLVER);
	}

	public <T> T assertEntityCreatedAndGet(GenericType<T> genericType) {
		return assertEntityCreatedAndGet(genericType, EntityResolver.GENERIC_TYPE_RESOLVER);
	}

	private <T> T assertEntityCreatedAndGet(Object typeOrGenericType, EntityResolver resolver) {
		try {
			return doAssertEntityCreatedAndGet(typeOrGenericType, resolver);
		} finally {
			clientResponse.close();
		}
	}

	private <T> T doAssertEntityCreatedAndGet(Object typeOrGenericType, EntityResolver resolver) {
		if (clientResponse.getStatus() == HttpStatus.SC_CONFLICT) {
			throw new HttpClientException("Entity already exists", clientResponse.getStatus());
		} else if (clientResponse.getStatus() != HttpStatus.SC_CREATED) {
			throw exceptionFactory.createException(clientResponse);
		}
		return resolver.getEntity(clientResponse, typeOrGenericType);
	}

}
