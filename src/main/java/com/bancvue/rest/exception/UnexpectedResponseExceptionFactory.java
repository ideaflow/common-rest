package com.bancvue.rest.exception;

import com.sun.jersey.api.client.ClientResponse;


public interface UnexpectedResponseExceptionFactory {

	RuntimeException createException(ClientResponse response);


	static class Default implements UnexpectedResponseExceptionFactory {

		@Override
		public RuntimeException createException(ClientResponse response) {
			return HttpClientException.unexpected(response.getStatus());
		}

	}

}
