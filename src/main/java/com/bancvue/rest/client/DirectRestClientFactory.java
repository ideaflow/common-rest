package com.bancvue.rest.client;

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