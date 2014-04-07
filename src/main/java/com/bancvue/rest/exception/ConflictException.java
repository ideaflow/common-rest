package com.bancvue.rest.exception;

import com.sun.jersey.api.client.ClientResponse;

public class ConflictException extends HttpClientException {

    public ConflictException(String message) {
        super(message, ClientResponse.Status.CONFLICT);
    }

}
