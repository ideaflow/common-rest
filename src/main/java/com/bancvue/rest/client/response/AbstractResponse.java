package com.bancvue.rest.client.response;

import com.bancvue.rest.exception.WebApplicationExceptionFactory;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public abstract class AbstractResponse {

	protected Response clientResponse;
	protected WebApplicationExceptionFactory exceptionFactory;

	public AbstractResponse(Response clientResponse, WebApplicationExceptionFactory exceptionFactory) {
		this.clientResponse = clientResponse;
		this.exceptionFactory = exceptionFactory;
	}

	protected abstract <T> T doGetValidatedResponse(Object responseType);

	public <T> T getValidatedResponse(Class<T> responseType) {
		return getValidatedResponseAndCloseClientResponse(responseType);
	}

	public <T> T getValidatedResponse(GenericType<T> responseType) {
		return getValidatedResponseAndCloseClientResponse(responseType);
	}

	private <T> T getValidatedResponseAndCloseClientResponse(Object responseType) {
		try {
			return doGetValidatedResponse(responseType);
		} finally {
			clientResponse.close();
		}
	}

	protected <T> T readEntity(Object responseType) {
		return ResponseHelper.readEntity(clientResponse, responseType);
	}

}
