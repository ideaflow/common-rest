package com.bancvue.rest.client;

import com.bancvue.rest.HttpClientException;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.http.HttpStatus;

public class DeleteResponse {

	private ClientResponse response;

	public DeleteResponse(ClientResponse response) {
		this.response = response;
	}

	public <T> T assertEntityDeletedAndGet(Class<T> type) {
		try {
			return doAssertEntityDeletedAndGet(type);
		} finally {
			response.close();
		}
	}

	private <T> T doAssertEntityDeletedAndGet(Class<T> type) {
		if (response.getStatus() == HttpStatus.SC_OK) {
			return response.getEntity(type);
		} else if (response.getStatus() == HttpStatus.SC_NO_CONTENT) {
			return null;
		}
		throw HttpClientException.unexpected(response.getStatus());
	}

}
