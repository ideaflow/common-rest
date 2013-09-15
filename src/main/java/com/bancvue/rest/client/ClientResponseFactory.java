package com.bancvue.rest.client;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;

public class ClientResponseFactory {

	public GetResponse get(WebResource resource) {
		ClientResponse response = resource
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.get(ClientResponse.class);

		return new GetResponse(response);
	}

	public CreateResponse create(WebResource resource, Object entity) {
		ClientResponse response = resource
				.type(MediaType.APPLICATION_JSON_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.entity(entity)
				.post(ClientResponse.class);

		return new CreateResponse(response);
	}

	public DeleteResponse delete(WebResource resource) {
		ClientResponse response = resource
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.delete(ClientResponse.class);

		return new DeleteResponse(response);
	}

	public UpdateResponse update(WebResource resource, Object entity) {
		ClientResponse response = resource
				.type(MediaType.APPLICATION_JSON_TYPE)
				.entity(entity)
				.put(ClientResponse.class);

		return new UpdateResponse(response);
	}

}
