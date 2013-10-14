package com.bancvue.rest.client;

import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;

public class ClientResponseFactory {

	private UnexpectedResponseExceptionFactory exceptionFactory;

	public ClientResponseFactory() {
		this(new UnexpectedResponseExceptionFactory.Default());
	}

	public ClientResponseFactory(UnexpectedResponseExceptionFactory exceptionFactory) {
		this.exceptionFactory = exceptionFactory;
	}

	public GetResponse get(WebResource resource) {
		ClientResponse response = resource
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.get(ClientResponse.class);

		return new GetResponse(response, exceptionFactory);
	}

	public PostResponse post(WebResource resource, Object entity) {
		ClientResponse response = resource
				.type(MediaType.APPLICATION_JSON_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.entity(entity)
				.post(ClientResponse.class);

		return new PostResponse(response, exceptionFactory);
	}

	public DeleteResponse delete(WebResource resource) {
		ClientResponse response = resource
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.delete(ClientResponse.class);

		return new DeleteResponse(response, exceptionFactory);
	}

	public PutResponse put(WebResource resource, Object entity) {
		ClientResponse response = resource
				.type(MediaType.APPLICATION_JSON_TYPE)
				.entity(entity)
				.put(ClientResponse.class);

		return new PutResponse(response, exceptionFactory);
	}

}
