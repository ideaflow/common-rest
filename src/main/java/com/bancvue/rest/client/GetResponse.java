package com.bancvue.rest.client;

import com.bancvue.rest.HttpClientException;
import com.sun.jersey.api.client.ClientResponse;

public class GetResponse {

    private ClientResponse response;

    public GetResponse(ClientResponse response) {
        this.response = response;
    }

	public <T> T acquireResponseAsType(Class<T> type) {
        try {
            return doAcquireResponseAsType(type);
        } finally {
            response.close();
        }
    }

    public <T> T doAcquireResponseAsType(Class<T> type) {
        if (response.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
            return response.getEntity(type);
        } else {
            throw HttpClientException.unexpected(response.getStatus());
        }
    }

    public <T> T getResponseAsType(Class<T> type) {
        try {
            return doGetResponseAsType(type);
        } finally {
            response.close();
        }
    }

    private <T> T doGetResponseAsType(Class<T> type) {
        if (response.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
            return response.getEntity(type);
        } else if (response.getStatus() == ClientResponse.Status.NOT_FOUND.getStatusCode()) {
            return null;
        } else {
            throw HttpClientException.unexpected(response.getStatus());
        }
    }

}
