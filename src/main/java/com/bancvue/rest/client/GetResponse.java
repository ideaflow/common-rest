package com.bancvue.rest.client;

import com.bancvue.rest.exception.NotFoundException;
import com.bancvue.rest.exception.SeeOtherException;
import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory;
import org.apache.http.HttpStatus;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class GetResponse {

	private Response clientResponse;
	private UnexpectedResponseExceptionFactory exceptionFactory;

	public GetResponse(Response clientResponse, UnexpectedResponseExceptionFactory exceptionFactory) {
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
		if (clientResponse.getStatus() == HttpStatus.SC_OK) {
			return resolver.getEntity(clientResponse, typeOrGenericType);
		} else if (clientResponse.getStatus() == HttpStatus.SC_SEE_OTHER) {
			MultivaluedMap<String, Object> headers = clientResponse.getHeaders();
			String otherLocation = headers.containsKey("Location") ? headers.get("Location").get(0).toString() : "";
			throw new SeeOtherException(otherLocation);
		} else if (clientResponse.getStatus() == HttpStatus.SC_NOT_FOUND) {
			throw new NotFoundException(clientResponse.readEntity(String.class));
		} else {
			throw exceptionFactory.createException(clientResponse);
		}
	}
}
