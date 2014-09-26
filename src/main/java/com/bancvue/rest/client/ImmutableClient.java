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
package com.bancvue.rest.client;

import java.net.URI;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

/**
 * An immutable wrapper around a jax-rs {@link javax.ws.rs.client.Client Client}
 */
public class ImmutableClient {

	 public static ImmutableClient createDefault() {
		return new ImmutableClient()
				.property(ClientProperties.FOLLOW_REDIRECTS, false);
	}


	private Client client;

	public ImmutableClient() {
		this(new ClientConfig());
	}

	public ImmutableClient(ClientConfig config) {
		client = ClientBuilder.newClient(config);
	}

	private ImmutableClient(Client client) {
		this.client = client;
	}

	private Client cloneClient() {
		return ClientBuilder.newClient(client.getConfiguration());
	}

	/**
	 * @see javax.ws.rs.client.Client#getConfiguration()
	 */
	public Configuration getConfiguration() {
		return client.getConfiguration();
	}

	/**
	 * @see javax.ws.rs.client.Client#property(String, Object)
	 */
	public ImmutableClient property(String name, Object value) {
		return new ImmutableClient(cloneClient().property(name, value));
	}

	/**
	 * @see javax.ws.rs.client.Client#register(Class)
	 */
	public ImmutableClient register(Class<?> componentClass) {
		return new ImmutableClient(cloneClient().register(componentClass));
	}

	/**
	 * @see javax.ws.rs.client.Client#register(Class, int)
	 */
	public ImmutableClient register(Class<?> componentClass, int priority) {
		return new ImmutableClient(cloneClient().register(componentClass, priority));
	}


	/**
	 * @see javax.ws.rs.client.Client#target(String)
	 */
	public WebTarget target(String uri) {
		return client.target(uri);
	}

	/**
	 * @see javax.ws.rs.client.Client#target(URI)
	 */
	public WebTarget target(URI uri) {
		return client.target(uri);
	}

	/**
	 * @see javax.ws.rs.client.Client#target(javax.ws.rs.core.UriBuilder)
	 */
	public WebTarget target(UriBuilder uriBuilder) {
		return client.target(uriBuilder);
	}

	/**
	 * @see javax.ws.rs.client.Client#target(javax.ws.rs.core.Link)
	 */
	public WebTarget target(Link link) {
		return client.target(link);
	}

}
