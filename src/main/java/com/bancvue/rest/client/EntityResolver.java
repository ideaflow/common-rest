package com.bancvue.rest.client;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public interface EntityResolver {

	<T> T getEntity(Response response, Object type);


	EntityResolver CLASS_RESOLVER = new EntityResolver() {
		@Override
		public <T> T getEntity(Response response, Object type) {
			return response.readEntity((Class<T>) type);
		}
	};

	EntityResolver GENERIC_TYPE_RESOLVER = new EntityResolver() {
		@Override
		public <T> T getEntity(Response response, Object type) {
			return response.readEntity((GenericType<T>) type);
		}
	};

}