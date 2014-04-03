package com.bancvue.rest.client;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public abstract class AbstractBancvueRestClientFactory<T> implements BancvueRestClientFactory<T> {

	private String host;

	public AbstractBancvueRestClientFactory(String host) {
		this.host = host;
	}

	public String getHost() {
		return host;
	}

	public WebResource createWebResource() {
		URI uri = UriBuilder.fromUri(host).build();

		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(clientConfig);

		client.setFollowRedirects(false);

		return client.resource(uri);
	}

	public abstract T createClient();

}