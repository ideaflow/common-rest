package com.bancvue.rest.exception;


import com.bancvue.rest.client.response.ResponseHelper;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.http.HttpStatus;


public interface WebApplicationExceptionFactory {

	WebApplicationException createException(Response response, Object responseType);


	static class Default implements WebApplicationExceptionFactory {

		public static final String ENTITY_ALREADY_EXISTS = "Entity already exists";

		@Override
		public WebApplicationException createException(Response response, Object responseType) {
			switch (response.getStatus()) {
				case HttpStatus.SC_UNPROCESSABLE_ENTITY:
					String msg = response.readEntity(String.class);
					return new ValidationException(msg);
				case HttpStatus.SC_CONFLICT:
					return createConflictException(response, responseType);
				case HttpStatus.SC_NOT_FOUND:
					String notFoundMessage = response.readEntity(String.class);
					return new NotFoundException(notFoundMessage);
				case HttpStatus.SC_SEE_OTHER:
					MultivaluedMap<String, Object> headers = response.getHeaders();
					String otherLocation = headers.containsKey("Location") ? headers.get("Location").get(0).toString() : "";
					return new SeeOtherException(otherLocation);
				default:
					return new WebApplicationException(response);
			}
		}

		private <T> WebApplicationException createConflictException(Response clientResponse, Object typeOrGenericType) {
			T entity = ResponseHelper.readEntity(clientResponse, typeOrGenericType);
			if (ResponseHelper.hasData(entity)) {
				throw new ConflictingEntityException(ENTITY_ALREADY_EXISTS, entity);
			}
			throw new ConflictException(ENTITY_ALREADY_EXISTS);
		}

	}

}
