package com.bancvue.rest.client;

public interface BancvueRestClientFactory<T> {

	public T createClient();
}
