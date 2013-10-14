package com.bancvue.rest.client;

import com.bancvue.rest.exception.HttpClientException;
import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import org.apache.http.HttpStatus;

public class GetResponse {

	private ClientResponse clientResponse;
	private UnexpectedResponseExceptionFactory exceptionFactory;

	public GetResponse(ClientResponse clientResponse, UnexpectedResponseExceptionFactory exceptionFactory) {
		this.clientResponse = clientResponse;
		this.exceptionFactory = exceptionFactory;
	}

	public <T> T getResponseAsType(Class<T> type) {
		return getResponseAsType(type, EntityResolver.CLASS_RESOLVER);
	}

	public <T> T getResponseAsType(GenericType<T> genericType) {
		return getResponseAsType(genericType, EntityResolver.GENERIC_TYPE_RESOLVER);
	}

	private <T> T getResponseAsType(Object typeOrGenericType, EntityResolver resolver) {
		T typedResponse = getResponseAsTypeOrNull(typeOrGenericType, resolver);
		if (typedResponse == null) {
			throw new HttpClientException("No entity found for location=" + clientResponse.getLocation(), ClientResponse.Status.NOT_FOUND);
		}
		return typedResponse;
	}

	public <T> T getResponseAsTypeOrNull(Class<T> type) {
		return getResponseAsTypeOrNull(type, EntityResolver.CLASS_RESOLVER);
	}

	public <T> T getResponseAsTypeOrNull(GenericType<T> genericType) {
		return getResponseAsTypeOrNull(genericType, EntityResolver.GENERIC_TYPE_RESOLVER);
	}

	private <T> T getResponseAsTypeOrNull(Object typeOrGenericType, EntityResolver resolver) {
		try {
			return doGetResponseAsTypeOrNull(typeOrGenericType, resolver);
		} finally {
			clientResponse.close();
		}
	}

	private <T> T doGetResponseAsTypeOrNull(Object typeOrGenericType, EntityResolver resolver) {
		if (clientResponse.getStatus() == HttpStatus.SC_OK) {
			return resolver.getEntity(clientResponse, typeOrGenericType);
		} else if (clientResponse.getStatus() == HttpStatus.SC_NOT_FOUND) {
			return null;
		} else {
			throw exceptionFactory.createException(clientResponse);
		}
	}

}
