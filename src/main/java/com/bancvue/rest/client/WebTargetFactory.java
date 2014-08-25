package com.bancvue.rest.client;

import java.net.URI;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

@Deprecated
@NoArgsConstructor
@AllArgsConstructor
public class WebTargetFactory {

	// TODO: Remove this once all projects have been converted to use the jaxrsClient bean defined in common-spring-boot
	@Deprecated
	public static Client createDefaultJaxrsClient() {
		return ClientBuilder.newClient(new ClientConfig()
				.property(ClientProperties.FOLLOW_REDIRECTS, false));
	}

	private ClientConfig clientConfig;

	public WebTarget create(String uriString) {
		URI uri = createUri(uriString);
		Client client = (clientConfig == null) ? createDefaultJaxrsClient() : ClientBuilder.newClient(clientConfig);
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

	public static class MalformedUriException extends RuntimeException {
		public MalformedUriException(String uriString) {
			super("Input uri='" + uriString + "' has no scheme or host (does the uri start with 'http://'?)");
		}
	}

}
