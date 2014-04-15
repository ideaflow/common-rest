package com.bancvue.rest.client;

import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ClientResponseFactory {

	private UnexpectedResponseExceptionFactory exceptionFactory;

	public ClientResponseFactory() {
		this(new UnexpectedResponseExceptionFactory.Default());
	}

	public ClientResponseFactory(UnexpectedResponseExceptionFactory exceptionFactory) {
		this.exceptionFactory = exceptionFactory;
	}

	public GetResponse get(WebTarget resource) {
		Response response = resource
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get(Response.class);

		return new GetResponse(response, exceptionFactory);
	}

	public CreateResponse createWithPost(WebTarget resource, Object entity) {
		Response response = resource
				.request(MediaType.APPLICATION_JSON_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(entity, MediaType.APPLICATION_JSON_TYPE), Response.class);

		return new CreateResponse(response, exceptionFactory);
	}

	public DeleteResponse delete(WebTarget resource) {
		Response response = resource
				.request()
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.delete(Response.class);

		return new DeleteResponse(response, exceptionFactory);
	}

	public UpdateResponse updateWithPut(WebTarget resource, Object entity) {
		Response response = resource
				.request(MediaType.APPLICATION_JSON_TYPE)
				.put(Entity.entity(entity, MediaType.APPLICATION_JSON_TYPE), Response.class);

		return new UpdateResponse(response, exceptionFactory);
	}

}
