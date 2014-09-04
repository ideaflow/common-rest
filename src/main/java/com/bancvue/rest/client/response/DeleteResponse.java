package com.bancvue.rest.client.response;

import com.bancvue.rest.exception.WebApplicationExceptionFactory;
import javax.ws.rs.core.Response;
import org.apache.http.HttpStatus;

public class DeleteResponse extends AbstractResponse {

	public DeleteResponse(Response clientResponse, WebApplicationExceptionFactory exceptionFactory) {
		super(clientResponse, exceptionFactory);
	}

	protected <T> T doGetValidatedResponse(Object responseType) {
		switch (clientResponse.getStatus()) {
			case HttpStatus.SC_OK:
				return readEntity(responseType);
			case HttpStatus.SC_NO_CONTENT:
				return null;
			default:
				throw exceptionFactory.createException(clientResponse, responseType);
		}
	}

}
