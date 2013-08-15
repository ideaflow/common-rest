package com.bancvue.rest.client;

import com.bancvue.rest.HttpClientException;
import com.sun.jersey.api.client.ClientResponse;

public class CreateResponse {

    private ClientResponse response;

    public CreateResponse(ClientResponse response) {
        this.response = response;
    }

    public <T> T assertEntityCreatedAndGet(Class<T> type) {
        try {
            return doAssertEntityCreatedAndGet(type);
        } finally {
            response.close();
        }
    }

    private <T> T doAssertEntityCreatedAndGet(Class<T> type) {
        if (response.getStatus() == ClientResponse.Status.CONFLICT.getStatusCode()) {
            throw new HttpClientException("Entity already exists", response.getStatus());
        // TODO: need status enum
        } else if (response.getStatus() == 422) { // Unprocessable Entity
            String message = response.getEntity(String.class);
            throw new HttpClientException(message, response.getStatus());
        } else if (response.getStatus() != ClientResponse.Status.CREATED.getStatusCode()) {
            // TODO: need to handle 'request failed validation' response
            throw HttpClientException.unexpected(response.getStatus());
        }
        return response.getEntity(type);
    }

}
