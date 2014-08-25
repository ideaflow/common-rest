package com.bancvue.rest.client;

public interface RestClientFactory<T> {

	public T createClient();
}
