package com.bancvue.rest.client.request;

import com.bancvue.rest.client.response.CreateResponse;
import com.bancvue.rest.client.response.DeleteResponse;
import com.bancvue.rest.client.response.GetResponse;
import com.bancvue.rest.client.response.UpdateResponse;
import com.bancvue.rest.exception.DefaultWebApplicationExceptionFactory;
import com.bancvue.rest.exception.WebApplicationExceptionFactory;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class BasicClientRequest implements ClientRequest {

	private WebTarget resource;
	private RequestParams requestParams;
	private WebApplicationExceptionFactory exceptionFactory;

	public BasicClientRequest(WebTarget resource) {
		this(resource, null, null);
	}

	public BasicClientRequest(WebTarget resource, RequestParams requestParams) {
		this(resource, requestParams, null);
	}

	public BasicClientRequest(WebTarget resource, WebApplicationExceptionFactory exceptionFactory) {
		this(resource, null, exceptionFactory);
	}

	public BasicClientRequest(WebTarget resource, RequestParams requestParams, WebApplicationExceptionFactory exceptionFactory) {
		this.resource = resource;
		this.requestParams = requestParams != null ? requestParams : RequestParams.jsonRequest();
		this.exceptionFactory = exceptionFactory != null ? exceptionFactory : new DefaultWebApplicationExceptionFactory();
	}

	public ClientRequest path(String path) {
		return createExecutor(resource.path(path));
	}

	public ClientRequest queryParam(String name, Object... values) {
		return createExecutor(resource.queryParam(name, values));
	}

	private ClientRequest createExecutor(WebTarget resource) {
		return new BasicClientRequest(resource, exceptionFactory);
	}

	public ClientRequest entityType(MediaType entityType) {
		return createExecutor(requestParams.entityType(entityType));
	}

	public ClientRequest request(MediaType... mediaTypes) {
		return createExecutor(requestParams.request(mediaTypes));
	}

	public ClientRequest accept(MediaType... mediaTypes) {
		return createExecutor(requestParams.accept(mediaTypes));
	}

	public ClientRequest header(String name, Object value) {
		return createExecutor(requestParams.header(name, value));
	}

	public ClientRequest property(String name, Object value) {
		return createExecutor(requestParams.property(name, value));
	}

	public ClientRequest cookie(String name, String value) {
		return cookie(new Cookie(name, value));
	}

	public ClientRequest cookie(Cookie cookie) {
		return createExecutor(requestParams.cookie(cookie));
	}

	private ClientRequest createExecutor(RequestParams requestParams) {
		return new BasicClientRequest(resource, requestParams, exceptionFactory);
	}


	private Invocation.Builder createRequest() {
		return requestParams.createInvocation(resource);
	}

	public GetResponse get() {
		Response response = createRequest()
				.get(Response.class);

		return new GetResponse(response, exceptionFactory);
	}

	public CreateResponse createWithPost(Object entity) {
		Response response = createRequest()
				.post(Entity.entity(entity, requestParams.getEntityType()), Response.class);

		return new CreateResponse(response, exceptionFactory);
	}

	public DeleteResponse delete() {
		Response response = createRequest()
				.delete(Response.class);

		return new DeleteResponse(response, exceptionFactory);
	}

	public UpdateResponse updateWithPut(Object entity) {
		Response response = createRequest()
				.put(Entity.entity(entity, requestParams.getEntityType()), Response.class);

		return new UpdateResponse(response, exceptionFactory);
	}

}
