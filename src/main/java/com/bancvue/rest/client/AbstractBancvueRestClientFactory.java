package com.bancvue.rest.client;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public abstract class AbstractBancvueRestClientFactory<T> implements BancvueRestClientFactory<T> {

	private String host;

	public AbstractBancvueRestClientFactory(String host) {
		this.host = host;
	}

	public String getHost() {
		return host;
	}

	protected WebTarget createWebResource() {
		URI uri = UriBuilder.fromUri(host).build();

		Client client = ClientBuilder.newClient(new ClientConfig()
						.register(JacksonFeature.class)
						.property(ClientProperties.FOLLOW_REDIRECTS, false)
		);

		return client.target(uri);
	}

	public abstract T createClient();

}