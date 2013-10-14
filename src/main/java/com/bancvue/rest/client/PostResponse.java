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

	public <T> T assertEntityCreatedAndGetResponse(Class<T> type) {
		return assertEntityCreatedAndGetResponse(type, EntityResolver.CLASS_RESOLVER);
	}

	public <T> T assertEntityCreatedAndGetResponse(GenericType<T> genericType) {
		return assertEntityCreatedAndGetResponse(genericType, EntityResolver.GENERIC_TYPE_RESOLVER);
	}

	private <T> T assertEntityCreatedAndGetResponse(Object typeOrGenericType, EntityResolver resolver) {
		try {
			return doAssertEntityCreatedAndGetResponse(typeOrGenericType, resolver);
		} finally {
			clientResponse.close();
		}
	}

	private <T> T doAssertEntityCreatedAndGetResponse(Object typeOrGenericType, EntityResolver resolver) {
		if (clientResponse.getStatus() == HttpStatus.SC_CONFLICT) {
			throw new HttpClientException("Entity already exists", clientResponse.getStatus());
		} else if (clientResponse.getStatus() != HttpStatus.SC_CREATED) {
			throw exceptionFactory.createException(clientResponse);
		}
		return resolver.getEntity(clientResponse, typeOrGenericType);
	}

}
