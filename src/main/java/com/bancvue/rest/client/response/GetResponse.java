package com.bancvue.rest.client.response;

import com.bancvue.rest.exception.WebApplicationExceptionFactory;
import javax.ws.rs.core.Response;
import org.apache.http.HttpStatus;

public class GetResponse extends AbstractResponse {

	public GetResponse(Response clientResponse, WebApplicationExceptionFactory exceptionFactory) {
		super(clientResponse, exceptionFactory);
	}

	protected <T> T doGetValidatedResponse(Object responseType) {
		if (clientResponse.getStatus() == HttpStatus.SC_OK) {
			return readEntity(responseType);
		} else {
			throw exceptionFactory.createException(clientResponse, responseType);
		}
	}

}
