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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public abstract class AbstractResponse {

	protected Response clientResponse;
	protected WebApplicationExceptionFactory exceptionFactory;

	public AbstractResponse(Response clientResponse, WebApplicationExceptionFactory exceptionFactory) {
		this.clientResponse = clientResponse;
		this.exceptionFactory = exceptionFactory;
	}

	protected abstract <T> T doGetValidatedResponse(Object responseType);

	public Response getClientResponse() {
		return clientResponse;
	}

	public <T> T getValidatedResponse(Class<T> responseType) {
		return getValidatedResponseAndCloseClientResponse(responseType);
	}

	public <T> T getValidatedResponse(GenericType<T> responseType) {
		return getValidatedResponseAndCloseClientResponse(responseType);
	}

	private <T> T getValidatedResponseAndCloseClientResponse(Object responseType) {
		try {
			return doGetValidatedResponse(responseType);
		} finally {
			clientResponse.close();
		}
	}

	protected <T> T readEntity(Object responseType) {
		return ResponseHelper.readEntity(clientResponse, responseType);
	}

}
