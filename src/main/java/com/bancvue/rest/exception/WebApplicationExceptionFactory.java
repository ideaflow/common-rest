package com.bancvue.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public interface WebApplicationExceptionFactory {

	WebApplicationException createException(Response response, Object responseType);

}
