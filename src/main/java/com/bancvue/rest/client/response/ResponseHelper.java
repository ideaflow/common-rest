package com.bancvue.rest.client.response;

import com.bancvue.rest.Envelope;
import com.bancvue.rest.exception.ConflictException;
import com.bancvue.rest.exception.ConflictingEntityException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public class ResponseHelper {

	public static final String ENTITY_ALREADY_EXISTS = "Entity already exists";

	public static <T> boolean hasData(T entity) {
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

	public static <T> WebApplicationException createConflictException(Response clientResponse, Object typeOrGenericType) {
		T entity = readEntity(clientResponse, typeOrGenericType);
		if (hasData(entity)) {
			throw new ConflictingEntityException(ENTITY_ALREADY_EXISTS, entity);
		}
		throw new ConflictException(ENTITY_ALREADY_EXISTS);
	}


}
