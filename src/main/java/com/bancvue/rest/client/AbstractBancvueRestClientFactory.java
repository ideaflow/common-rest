package com.bancvue.rest.client;

public abstract class AbstractBancvueRestClientFactory<T> implements BancvueRestClientFactory<T> {

	private String host;

	public AbstractBancvueRestClientFactory(String host) {
		this.host = host;
	}

	public String getHost() {
		return host;
	}

	public abstract T createClient();
}