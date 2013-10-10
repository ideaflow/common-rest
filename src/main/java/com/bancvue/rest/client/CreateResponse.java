package com.bancvue.rest.client;

import com.bancvue.rest.HttpClientException;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.http.HttpStatus;

public class CreateResponse {

	private ClientResponse response;

	public CreateResponse(ClientResponse response) {
		this.response = response;
	}

	public <T> T assertEntityCreatedAndGet(Class<T> type) {
		try {
			return doAssertEntityCreatedAndGet(type);
		} finally {
			response.close();
		}
	}

	private <T> T doAssertEntityCreatedAndGet(Class<T> type) {
		if (response.getStatus() == HttpStatus.SC_CONFLICT) {
			throw new HttpClientException("Entity already exists", response.getStatus());
		} else if (response.getStatus() == HttpStatus.SC_UNPROCESSABLE_ENTITY) {
			String message = response.getEntity(String.class);
			throw new HttpClientException(message, response.getStatus());
		} else if (response.getStatus() != HttpStatus.SC_CREATED) {
			// TODO: need to handle 'request failed validation' response
			throw HttpClientException.unexpected(response.getStatus());
		}
		return response.getEntity(type);
	}

}
