package com.bancvue.rest.client;

import com.bancvue.rest.HttpClientException;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.http.HttpStatus;

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
		if (response.getStatus() != HttpStatus.SC_NO_CONTENT) {
			throw HttpClientException.unexpected(response.getStatus());
		}
	}
}
