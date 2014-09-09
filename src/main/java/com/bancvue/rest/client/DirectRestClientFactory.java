package com.bancvue.rest.client;

import javax.ws.rs.client.Client;

public abstract class DirectRestClientFactory<T> implements RestClientFactory<T> {

	private ClientRequest clientRequest;

	public DirectRestClientFactory(Client client, String host) {
		this.clientRequest = new DirectClientRequest(client, host);
	}

	protected ClientRequest getClientRequest() {
		return clientRequest;
	}

	public abstract T createClient();

}