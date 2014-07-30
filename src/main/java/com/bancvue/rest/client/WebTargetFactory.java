package com.bancvue.rest.client;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class WebTargetFactory {
	public WebTarget create(String host) {
		URI uri = UriBuilder.fromUri(host).build();

		Client client = ClientBuilder.newClient(new ClientConfig()
						.property(ClientProperties.FOLLOW_REDIRECTS, false)
		);

		return client.target(uri);
	}
}
