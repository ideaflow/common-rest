package com.bancvue.rest.client;

import com.bancvue.rest.HttpClientException;
import com.sun.jersey.api.client.ClientResponse;

public class UpdateResponse {

	private ClientResponse response;

	public UpdateResponse(ClientResponse response) {
		this.response = response;
	}

	public void assertResponseSuccess() {
		try {
			doAssertResponseSuccess();
		} finally {
			response.close();
		}
	}

	private void doAssertResponseSuccess() {
		if (response.getStatus() != ClientResponse.Status.NO_CONTENT.getStatusCode()) {
			throw HttpClientException.unexpected(response.getStatus());
		}
	}
}
