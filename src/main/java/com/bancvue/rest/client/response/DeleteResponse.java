package com.bancvue.rest.client.response;

import com.bancvue.rest.exception.NotFoundException;
import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory;
import javax.ws.rs.core.Response;
import org.apache.http.HttpStatus;

public class DeleteResponse extends AbstractResponse {

	public DeleteResponse(Response clientResponse, UnexpectedResponseExceptionFactory exceptionFactory) {
		super(clientResponse, exceptionFactory);
	}

	protected <T> T doGetValidatedResponse(Object responseType) {
		switch (clientResponse.getStatus()) {
			case HttpStatus.SC_OK:
				return readEntity(responseType);
			case HttpStatus.SC_NO_CONTENT:
				return null;
			default:
				throw createResponseException();
		}
	}

	private RuntimeException createResponseException() {
		switch (clientResponse.getStatus()) {
			case HttpStatus.SC_NOT_FOUND:
				String msg = clientResponse.readEntity(String.class);
				return new NotFoundException(msg);
			default:
				return exceptionFactory.createException(clientResponse);
		}
	}

}
