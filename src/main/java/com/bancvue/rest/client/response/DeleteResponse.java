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
package com.bancvue.rest.client.response;

import com.bancvue.rest.exception.WebApplicationExceptionFactory;
import javax.ws.rs.core.Response;
import org.apache.http.HttpStatus;

public class DeleteResponse extends AbstractResponse {

	public DeleteResponse(Response clientResponse, WebApplicationExceptionFactory exceptionFactory) {
		super(clientResponse, exceptionFactory);
	}

	protected <T> T doGetValidatedResponse(Object responseType) {
		switch (clientResponse.getStatus()) {
			case HttpStatus.SC_OK:
				return readEntity(responseType);
			case HttpStatus.SC_NO_CONTENT:
				return null;
			default:
				throw exceptionFactory.createException(clientResponse, responseType);
		}
	}

}
