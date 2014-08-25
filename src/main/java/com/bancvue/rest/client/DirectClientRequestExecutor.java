package com.bancvue.rest.client;

import com.bancvue.rest.client.response.CreateResponse;
import com.bancvue.rest.client.response.DeleteResponse;
import com.bancvue.rest.client.response.GetResponse;
import com.bancvue.rest.client.response.UpdateResponse;
import com.bancvue.rest.exception.DefaultWebApplicationExceptionFactory;
import com.bancvue.rest.exception.WebApplicationExceptionFactory;
import java.net.URI;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

public class DirectClientRequestExecutor implements ClientRequestExecutor {

	private BasicClientRequestExecutor delegate;

	public DirectClientRequestExecutor(Client client, String uriString) {
		this(client, uriString, new DefaultWebApplicationExceptionFactory());
	}

	public DirectClientRequestExecutor(Client client, String uriString,
	                                   WebApplicationExceptionFactory exceptionFactory) {
		WebTarget webTarget = createWebTarget(client, uriString);
		delegate = new BasicClientRequestExecutor(webTarget, exceptionFactory);
	}

	private WebTarget createWebTarget(Client client, String uriString) {
		URI uri = createUri(uriString);
		return client.target(uri);
	}

	private URI createUri(String uriString) {
		URI uri = UriBuilder.fromUri(uriString).build();
		// if the uri is missing the host or scheme, WebTarget methods like path(..) will fail with a less than clear exception
		if (uri.getScheme() == null || uri.getHost() == null) {
			throw new MalformedUriException(uriString);
		}
		return uri;
	}

	@Override
	public WebTarget getBaseResource() {
		return delegate.getBaseResource();
	}

	@Override
	public GetResponse get(WebTarget resource) {
		return delegate.get(resource);
	}

	@Override
	public CreateResponse createWithPost(WebTarget resource, Object entity) {
		return delegate.createWithPost(resource, entity);
	}

	@Override
	public DeleteResponse delete(WebTarget resource) {
		return delegate.delete(resource);
	}

	@Override
	public UpdateResponse updateWithPut(WebTarget resource, Object entity) {
		return delegate.updateWithPut(resource, entity);
	}


	public static class MalformedUriException extends RuntimeException {
		public MalformedUriException(String uriString) {
			super("Input uri='" + uriString + "' has no scheme or host (does the uri start with 'http://'?)");
		}
	}

}
