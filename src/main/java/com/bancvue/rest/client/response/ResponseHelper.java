package com.bancvue.rest.client.response;

import com.bancvue.rest.envelope.Envelope;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public class ResponseHelper {

	public static <T> boolean isEntityNotNull(T entity) {
		if (entity instanceof Envelope) {
			return ((Envelope) entity).getData() != null;
		}
		return entity != null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T readEntity(Response clientResponse, Object responseType) {
		if (responseType instanceof Class) {
			return clientResponse.readEntity((Class<T>) responseType);
		} else if (responseType instanceof GenericType) {
			return clientResponse.readEntity((GenericType<T>) responseType);
		} else {
			throw new IllegalArgumentException("Response type must be either Class or GenericType, was=" + responseType);
		}
	}

}
