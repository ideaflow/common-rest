package com.bancvue.rest.client;

import javax.ws.rs.client.Client;

public abstract class DirectRestClientFactory<T> implements RestClientFactory<T> {

	private ClientRequestExecutor clientRequestExecutor;

	public DirectRestClientFactory(Client client, String host) {
		this.clientRequestExecutor = new DirectClientRequestExecutor(client, host);
	}

	protected ClientRequestExecutor getClientRequestExecutor() {
		return clientRequestExecutor;
	}

	public abstract T createClient();

}