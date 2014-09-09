package com.bancvue.rest.client;

import com.bancvue.rest.client.response.CreateResponse;
import com.bancvue.rest.client.response.DeleteResponse;
import com.bancvue.rest.client.response.GetResponse;
import com.bancvue.rest.client.response.UpdateResponse;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

public interface ClientRequest {

	public ClientRequest path(String path);

	public ClientRequest queryParam(String name, Object... values);

	public ClientRequest entityType(MediaType entityType);

	public ClientRequest request(MediaType... mediaTypes);

	public ClientRequest accept(MediaType... mediaTypes);

	public ClientRequest header(String name, Object value);

	public ClientRequest property(String name, Object value);

	public ClientRequest cookie(String name, String value);

	public ClientRequest cookie(Cookie cookie);


	public GetResponse get();

	public CreateResponse createWithPost(Object entity);

	public DeleteResponse delete();

	public UpdateResponse updateWithPut(Object entity);

}
