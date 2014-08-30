package com.bancvue.rest.client.response;

import com.bancvue.rest.client.EntityResolver;
import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory;
import com.bancvue.rest.exception.ValidationException;
import org.apache.http.HttpStatus;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public class CreateResponse {

	private Response clientResponse;
	private UnexpectedResponseExceptionFactory exceptionFactory;

	public CreateResponse(Response clientResponse, UnexpectedResponseExceptionFactory exceptionFactory) {
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
		switch (clientResponse.getStatus()) {
			case HttpStatus.SC_OK:
			case HttpStatus.SC_CREATED:
				return resolver.getEntity(clientResponse, typeOrGenericType);
			case HttpStatus.SC_NO_CONTENT:
				return null;
			case HttpStatus.SC_UNPROCESSABLE_ENTITY:
				String msg = EntityResolver.CLASS_RESOLVER.getEntity(clientResponse, String.class);
				throw new ValidationException(msg);
			case HttpStatus.SC_CONFLICT:
				ResponseHelper.handleSCConflict(clientResponse, resolver, typeOrGenericType);
			default:
				throw exceptionFactory.createException(clientResponse);
		}
	}
}
