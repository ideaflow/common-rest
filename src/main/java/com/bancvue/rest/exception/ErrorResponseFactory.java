/**
 * Copyright 2017 BancVue, LTD
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
package com.bancvue.rest.exception;

import javax.ws.rs.core.Response;
import java.util.Map;

public class ErrorResponseFactory {

	public static Response makeErrorResponse(Response.Status status, Exception ex) {
		return makeErrorResponse(status, ex.getMessage());
	}

	public static Response makeErrorResponse(Response.Status status, String messageTemplate, Object... args) {
		return buildResponse(status, ErrorEntity.create(status, messageTemplate, args));
	}

	public static Response buildResponse(Response.Status status, ErrorEntity errorEntity) {
		return Response.status(status)
				.entity(errorEntity)
				.type("application/json")
				.build();
	}

}
