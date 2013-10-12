package com.bancvue.rest.client;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

public interface EntityResolver {

	<T> T getEntity(ClientResponse response, Object type);


	EntityResolver CLASS_RESOLVER = new EntityResolver() {
		@Override
		public <T> T getEntity(ClientResponse response, Object type) {
			return response.getEntity((Class<T>) type);
		}
	};

	EntityResolver GENERIC_TYPE_RESOLVER = new EntityResolver() {
		@Override
		public <T> T getEntity(ClientResponse response, Object type) {
			return response.getEntity((GenericType<T>) type);
		}
	};

}