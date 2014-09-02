package com.bancvue.rest.client.response;

import com.bancvue.rest.exception.NotFoundException;
import com.bancvue.rest.exception.SeeOtherException;
import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.http.HttpStatus;

public class GetResponse extends AbstractResponse {

	public GetResponse(Response clientResponse, UnexpectedResponseExceptionFactory exceptionFactory) {
		super(clientResponse, exceptionFactory);
	}

	protected <T> T doGetValidatedResponse(Object responseType) {
		if (clientResponse.getStatus() == HttpStatus.SC_OK) {
			return readEntity(responseType);
		} else {
			throw createResponseException();
		}
	}

	private RuntimeException createResponseException() {
		switch (clientResponse.getStatus()) {
			case HttpStatus.SC_NOT_FOUND:
				String msg = clientResponse.readEntity(String.class);
				return new NotFoundException(msg);
			case HttpStatus.SC_SEE_OTHER:
				MultivaluedMap<String, Object> headers = clientResponse.getHeaders();
				String otherLocation = headers.containsKey("Location") ? headers.get("Location").get(0).toString() : "";
				return new SeeOtherException(otherLocation);
			default:
				return exceptionFactory.createException(clientResponse);
		}
	}

}
