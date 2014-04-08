package com.bancvue.rest.client;

import com.bancvue.rest.exception.ConflictException;
import com.bancvue.rest.exception.HttpClientException;
import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory;
import com.bancvue.rest.exception.ValidationException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

import org.apache.http.HttpStatus;

public class CreateResponse {

	private ClientResponse clientResponse;
	private UnexpectedResponseExceptionFactory exceptionFactory;

	public CreateResponse(ClientResponse clientResponse, UnexpectedResponseExceptionFactory exceptionFactory) {
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
		if (clientResponse.getStatus() == HttpStatus.SC_BAD_REQUEST) {
			String msg = EntityResolver.CLASS_RESOLVER.getEntity(clientResponse, String.class);
			throw new ValidationException(msg);
		} else if (clientResponse.getStatus() == HttpStatus.SC_CONFLICT) {
			throw new ConflictException("Entity already exists");
		} else if (clientResponse.getStatus() != HttpStatus.SC_CREATED) {
			throw exceptionFactory.createException(clientResponse);
		}
		return resolver.getEntity(clientResponse, typeOrGenericType);
	}
}
