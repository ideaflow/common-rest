package com.bancvue.rest.client.response;

import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory;
import com.bancvue.rest.exception.ValidationException;
import javax.ws.rs.core.Response;
import org.apache.http.HttpStatus;

public class CreateResponse extends AbstractResponse {

	public CreateResponse(Response clientResponse, UnexpectedResponseExceptionFactory exceptionFactory) {
		super(clientResponse, exceptionFactory);
	}

	protected <T> T doGetValidatedResponse(Object responseType) {
		switch (clientResponse.getStatus()) {
			case HttpStatus.SC_OK:
			case HttpStatus.SC_CREATED:
				return readEntity(responseType);
			case HttpStatus.SC_NO_CONTENT:
				return null;
			default:
				throw createResponseException(responseType);
		}
	}

	private RuntimeException createResponseException(Object responseType) {
		switch (clientResponse.getStatus()) {
			case HttpStatus.SC_UNPROCESSABLE_ENTITY:
				String msg = clientResponse.readEntity(String.class);
				return new ValidationException(msg);
			case HttpStatus.SC_CONFLICT:
				return ResponseHelper.createConflictException(clientResponse, responseType);
			default:
				return exceptionFactory.createException(clientResponse);
		}
	}

}
