package com.bancvue.rest.client;

import com.bancvue.rest.client.request.ClientRequest;
import com.bancvue.rest.client.request.DirectClientRequest;

public abstract class DirectRestClientFactory<T> implements RestClientFactory<T> {

	private ClientRequest clientRequest;

	public DirectRestClientFactory(ImmutableClient client, String host) {
		this.clientRequest = new DirectClientRequest(client, host);
	}

	protected ClientRequest getClientRequest() {
		return clientRequest;
	}

	public abstract T createClient();

}