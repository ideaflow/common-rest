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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

@SuppressWarnings("unchecked")
public class RequestParams {

	public static RequestParams jsonRequest() {
		return new RequestParams()
				.request(MediaType.APPLICATION_JSON_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.entityType(MediaType.APPLICATION_JSON_TYPE);
	}

	private MediaType entityType;
	private MediaType[] requestTypes;
	private MediaType[] acceptTypes;
	private MultivaluedHashMap<String, Object> headers;
	private HashMap<String, Object> properties;
	private ArrayList<Cookie> cookies;

	public RequestParams() {
		this.entityType = null;
		this.requestTypes = new MediaType[0];
		this.acceptTypes = new MediaType[0];
		this.headers = new MultivaluedHashMap<>();
		this.properties = new HashMap<>();
		this.cookies = new ArrayList<>();
	}

	public RequestParams(MediaType entityType, MediaType[] requestTypes, MediaType[] acceptTypes,
	                     MultivaluedHashMap<String, Object> headers, HashMap<String, Object> properties,
	                     ArrayList<Cookie> cookies) {
		this.entityType = entityType;
		this.requestTypes = requestTypes;
		this.acceptTypes = acceptTypes;
		this.headers = headers;
		this.properties = properties;
		this.cookies = cookies;
	}

	private MediaType[] add(MediaType[] initial, MediaType... added) {
		ArrayList<MediaType> list = new ArrayList<>();
		Collections.addAll(list, initial);
		Collections.addAll(list, added);
		return list.toArray(new MediaType[list.size()]);
	}

	public RequestParams entityType(MediaType newEntityType) {
		return new RequestParams(newEntityType, requestTypes, acceptTypes, headers, properties, cookies);
	}

	public RequestParams request(MediaType... mediaTypes) {
		return new RequestParams(entityType, add(requestTypes, mediaTypes), acceptTypes, headers, properties, cookies);
	}

	public RequestParams accept(MediaType... mediaTypes) {
		return new RequestParams(entityType, requestTypes, add(acceptTypes, mediaTypes), headers, properties, cookies);
	}

	public RequestParams header(String name, Object value) {
		MultivaluedHashMap headersClone = new MultivaluedHashMap<>(headers);
		headersClone.add(name, value);
		return new RequestParams(entityType, requestTypes, acceptTypes, headersClone, properties, cookies);
	}

	public RequestParams property(String name, Object value) {
		HashMap<String, Object> propertiesClone = new HashMap<>(properties);
		propertiesClone.put(name, value);
		return new RequestParams(entityType, requestTypes, acceptTypes, headers, propertiesClone, cookies);
	}

	public RequestParams cookie(Cookie cookie) {
		ArrayList<Cookie> cookiesClone = new ArrayList<>(cookies);
		cookiesClone.add(cookie);
		return new RequestParams(entityType, requestTypes, acceptTypes, headers, properties, cookiesClone);
	}


	public Invocation.Builder createInvocation(WebTarget webTarget) {
		Invocation.Builder invocation = webTarget.request(requestTypes)
				.accept(acceptTypes);

		for (String key : headers.keySet()) {
			for (Object value : headers.get(key)) {
				invocation = invocation.header(key, value);
			}
		}

		for (Cookie cookie : cookies) {
			invocation = invocation.cookie(cookie);
		}

		for (String key : properties.keySet()) {
			invocation = invocation.property(key, properties.get(key));
		}
		return invocation;
	}

	public MediaType getEntityType() {
		return entityType;
	}
}
