package com.bancvue.rest.exception;


import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;


public interface UnexpectedResponseExceptionFactory {

	RuntimeException createException(Response response);


	static class Default implements UnexpectedResponseExceptionFactory {

		@Override
		public RuntimeException createException(Response response) {
			return new WebApplicationException(response);
		}

	}

}
