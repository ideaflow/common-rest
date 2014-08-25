package com.bancvue.rest.client;

import com.bancvue.rest.client.response.CreateResponse;
import com.bancvue.rest.client.response.DeleteResponse;
import com.bancvue.rest.client.response.GetResponse;
import com.bancvue.rest.client.response.UpdateResponse;
import javax.ws.rs.client.WebTarget;

public interface ClientRequestExecutor {

	public WebTarget getBaseResource();

	public GetResponse get(WebTarget resource);

	public CreateResponse createWithPost(WebTarget resource, Object entity);

	public DeleteResponse delete(WebTarget resource);

	public UpdateResponse updateWithPut(WebTarget resource, Object entity);

}
