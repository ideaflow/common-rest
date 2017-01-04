/**
 * Copyright 2017 BancVue, LTD
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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;
import org.glassfish.jersey.server.validation.ValidationError;

import javax.ws.rs.core.Response;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorEntity {

	private ResponseStatus status;
	private String message;
	private Object[] args;
	private List<ValidationError> validationErrors;


	static ErrorEntity create(Response.StatusType status, String messageTemplate, Object ... args) {
		return createBuilder(status, messageTemplate, args).build();
	}

	static ErrorEntity.ErrorEntityBuilder createBuilder(Response.StatusType status, String messageTemplate, Object ... args) {
		String message = (args != null) && (args.length > 0) ? String.format(messageTemplate, args) : messageTemplate;

		ResponseStatus responseStatus = ResponseStatus.builder()
				.statusCode(status.getStatusCode())
				.family(status.getFamily())
				.reasonPhrase(status.getReasonPhrase())
				.build();

		return ErrorEntity.builder()
				.status(responseStatus)
				.message(message)
				.args(args);
	}

}