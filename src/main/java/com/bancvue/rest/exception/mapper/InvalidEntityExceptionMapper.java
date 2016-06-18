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
package com.bancvue.rest.exception.mapper;

import com.bancvue.rest.envelope.DefaultEnvelope;
import com.bancvue.rest.envelope.Envelope;
import com.bancvue.rest.jaxrs.UnprocessableEntityStatusType;
import java.util.List;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.glassfish.jersey.server.validation.ValidationError;
import org.glassfish.jersey.server.validation.internal.ValidationHelper;

@Provider
public class InvalidEntityExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
	@Override
	public Response toResponse(ConstraintViolationException exception) {
		Envelope<List<ValidationError>> envelope =
				new DefaultEnvelope.Builder<List<ValidationError>>(ValidationHelper.constraintViolationToValidationErrors(exception)).build();
		return Response.status(new UnprocessableEntityStatusType()).type(MediaType.APPLICATION_JSON_TYPE).entity(envelope).build();
	}
}
