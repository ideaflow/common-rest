package com.bancvue.rest.client;

import com.bancvue.rest.HttpClientException;
import com.sun.jersey.api.client.ClientResponse;

public class DeleteResponse {

    private ClientResponse response;

    public DeleteResponse(ClientResponse response) {
        this.response = response;
    }

    public <T> T assertEntityDeletedAndGet(Class<T> type) {
        try {
            return doAssertEntityDeletedAndGet(type);
        } finally {
            response.close();
        }
    }

    private <T> T doAssertEntityDeletedAndGet(Class<T> type) {
	    if (response.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
		    return response.getEntity(type);
	    } else if (response.getStatus() == ClientResponse.Status.NO_CONTENT.getStatusCode()) {
		    return null;
        }
		throw HttpClientException.unexpected(response.getStatus());
    }

}
