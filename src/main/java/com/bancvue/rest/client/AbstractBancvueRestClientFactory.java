package com.bancvue.rest.client;

import javax.ws.rs.client.WebTarget;

public abstract class AbstractBancvueRestClientFactory<T> implements BancvueRestClientFactory<T> {

	private String host;

	public AbstractBancvueRestClientFactory(String host) {
		this.host = host;
	}

	public String getHost() {
		return host;
	}

	protected WebTarget createWebResource() {
		return new WebTargetFactory().create(host);
	}

	public abstract T createClient();

}