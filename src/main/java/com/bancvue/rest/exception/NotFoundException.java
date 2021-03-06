/**
 * Copyright 2014 BancVue, LTD
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bancvue.rest.exception;

import javax.ws.rs.core.Response;

public class NotFoundException extends javax.ws.rs.NotFoundException {

	public NotFoundException() {
		this((String) null);
	}

	public NotFoundException(String message) {
		super(ErrorResponseFactory.makeErrorResponse(Response.Status.NOT_FOUND, message));
	}

	public NotFoundException(ErrorEntity errorEntity) {
		super(ErrorResponseFactory.buildResponse(Response.Status.NOT_FOUND, errorEntity));
	}

}
