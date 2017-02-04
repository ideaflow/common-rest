/**
 * Copyright 2014 BancVue, LTD
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bancvue.rest.exception;

import com.bancvue.rest.client.response.ResponseHelper;
import org.apache.http.HttpStatus;
import org.glassfish.jersey.message.internal.MessageBodyProviderNotFoundException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class DefaultWebApplicationExceptionFactory implements WebApplicationExceptionFactory {

	public static final String ENTITY_ALREADY_EXISTS = "Entity already exists";

	@Override
	public WebApplicationException createException(Response response, Object responseType) {
		try {
			return createWebApplicationException(response, responseType);
		} catch (FailedToResolveErrorEntityException ex) {
			return new WebApplicationException(ex, response);
		}
	}

	private WebApplicationException createWebApplicationException(Response response, Object responseType) throws FailedToResolveErrorEntityException {
		switch (response.getStatus()) {
			case HttpStatus.SC_BAD_REQUEST:
				return new ValidationException(createErrorEntity(response));
			case HttpStatus.SC_NOT_FOUND:
				return new NotFoundException(createErrorEntity(response));
			case HttpStatus.SC_FORBIDDEN:
				return new ForbiddenException(createErrorEntity(response));
			case HttpStatus.SC_UNPROCESSABLE_ENTITY:
				// TODO:
				return new ValidationException(createErrorEntity(response));
			case HttpStatus.SC_SEE_OTHER:
				return createSeeOtherException(response);
			case HttpStatus.SC_CONFLICT:
				return createConflictException(response, responseType);
			default:
				return new WebApplicationException(response);
		}
	}

	private ErrorEntity createErrorEntity(Response response) throws FailedToResolveErrorEntityException {
		try {
			response.bufferEntity();
		} catch (Exception ex) {
			throw new FailedToResolveErrorEntityException("Failed to buffer entity", ex);
		}

		try {
			return response.readEntity(ErrorEntity.class);
		} catch (MessageBodyProviderNotFoundException ex) {
			throw new FailedToResolveErrorEntityException("Failed to resolve ErrorEntity from response, content=" + getContentAsString(response), ex);
		}
	}

	private String getContentAsString(Response response) {
		try {
			return response.readEntity(String.class);
		} catch (Exception ex) {
			return "Failed to read response content as string";
		}
	}

	private WebApplicationException createSeeOtherException(Response response) {
		MultivaluedMap<String, Object> headers = response.getHeaders();
		String otherLocation = headers.containsKey("Location") ? headers.get("Location").get(0).toString() : "";
		return new SeeOtherException(otherLocation);
	}

	private <T> WebApplicationException createConflictException(Response clientResponse, Object typeOrGenericType) {
		T entity = ResponseHelper.readEntity(clientResponse, typeOrGenericType);
		if (ResponseHelper.isEntityNotNull(entity)) {
			throw new ConflictException(ENTITY_ALREADY_EXISTS, entity);
		}
		throw new ConflictException(ENTITY_ALREADY_EXISTS);
	}

	private static class FailedToResolveErrorEntityException extends Exception {
		public FailedToResolveErrorEntityException(String message, Throwable cause) {
			super(message, cause);
		}
	}

}
