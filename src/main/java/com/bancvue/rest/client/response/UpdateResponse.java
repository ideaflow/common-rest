package com.bancvue.rest.client.response;

import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory;
import com.bancvue.rest.exception.ValidationException;
import javax.ws.rs.core.Response;
import org.apache.http.HttpStatus;

public class UpdateResponse extends AbstractResponse {

	public UpdateResponse(Response clientResponse, UnexpectedResponseExceptionFactory exceptionFactory) {
		super(clientResponse, exceptionFactory);
	}

	protected <T> T doGetValidatedResponse(Object responseType) {
		if (clientResponse.getStatus() == HttpStatus.SC_OK) {
			return readEntity(responseType);
		} else {
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
