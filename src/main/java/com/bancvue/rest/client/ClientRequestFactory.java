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

import com.bancvue.rest.client.request.ClientRequest;
import com.bancvue.rest.client.request.DirectClientRequest;

public class ClientRequestFactory {

	private ImmutableClient client;

	public ClientRequestFactory() {
		this(ImmutableClient.createDefault());
	}

	public ClientRequestFactory(ImmutableClient client) {
		this.client = client;
	}

	public ClientRequest createClientRequest(String host) {
		return new DirectClientRequest(client, host);
	}


	/**
	 * @see javax.ws.rs.client.Client#property(String, Object)
	 */
	public ClientRequestFactory property(String name, Object value) {
		return new ClientRequestFactory(client.property(name, value));
	}

	/**
	 * @see javax.ws.rs.client.Client#register(Class)
	 */
	public ClientRequestFactory register(Class<?> componentClass) {
		return new ClientRequestFactory(client.register(componentClass));
	}

	/**
	 * @see javax.ws.rs.client.Client#register(Class, int)
	 */
	public ClientRequestFactory register(Class<?> componentClass, int priority) {
		return new ClientRequestFactory(client.register(componentClass, priority));
	}

}
