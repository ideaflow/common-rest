/**
 * Copyright 2014 BancVue, LTD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bancvue.rest.client.request;

import com.bancvue.rest.client.ImmutableClient;
import com.bancvue.rest.exception.DefaultWebApplicationExceptionFactory;
import com.bancvue.rest.exception.WebApplicationExceptionFactory;
import java.net.URI;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import lombok.Delegate;

public class DirectClientRequest implements ClientRequest {

	@Delegate
	private BasicClientRequest delegate;

	public DirectClientRequest(ImmutableClient immutableClient, String uriString) {
		this(immutableClient, uriString, new DefaultWebApplicationExceptionFactory());
	}

	public DirectClientRequest(ImmutableClient client, String uriString,
	                           WebApplicationExceptionFactory exceptionFactory) {
		WebTarget webTarget = createWebTarget(client, uriString);
		delegate = new BasicClientRequest(webTarget, exceptionFactory);
	}

	private WebTarget createWebTarget(ImmutableClient client, String uriString) {
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


	public static class MalformedUriException extends RuntimeException {
		public MalformedUriException(String uriString) {
			super("Input uri='" + uriString + "' has no scheme or host (does the uri start with 'http://'?)");
		}
	}

}
