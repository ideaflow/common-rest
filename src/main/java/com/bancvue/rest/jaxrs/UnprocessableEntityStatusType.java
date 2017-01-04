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
package com.bancvue.rest.jaxrs;

import javax.ws.rs.core.Response;

public class UnprocessableEntityStatusType implements Response.StatusType {

	public static UnprocessableEntityStatusType INSTANCE = new UnprocessableEntityStatusType();

	@Override
	public int getStatusCode() {
		return 422;
	}

	@Override
	public Response.Status.Family getFamily() {
		return Response.Status.Family.CLIENT_ERROR;
	}

	@Override
	public String getReasonPhrase() {
		return "Unprocessable Entity";
	}

}
