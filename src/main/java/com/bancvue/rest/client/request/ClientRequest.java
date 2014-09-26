/**
 * Copyright 2014 BancVue, LTD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bancvue.rest.client.request;

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
