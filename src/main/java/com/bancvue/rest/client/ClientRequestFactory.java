package com.bancvue.rest.client;

import com.bancvue.rest.client.request.ClientRequest;
import com.bancvue.rest.client.request.DirectClientRequest;

public class ClientRequestFactory {

	private ImmutableClient client;

	public ClientRequestFactory() {
		this(ImmutableClient.createDefault());
	}

	public ClientRequestFactory(ImmutableClient client) {
		this.client = client;
	}

	public ClientRequest createClientRequest(String host) {
		return new DirectClientRequest(client, host);
	}


	/**
	 * @see javax.ws.rs.client.Client#property(String, Object)
	 */
	public ClientRequestFactory property(String name, Object value) {
		return new ClientRequestFactory(client.property(name, value));
	}

	/**
	 * @see javax.ws.rs.client.Client#register(Class)
	 */
	public ClientRequestFactory register(Class<?> componentClass) {
		return new ClientRequestFactory(client.register(componentClass));
	}

	/**
	 * @see javax.ws.rs.client.Client#register(Class, int)
	 */
	public ClientRequestFactory register(Class<?> componentClass, int priority) {
		return new ClientRequestFactory(client.register(componentClass, priority));
	}

}
