/*
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
package com.bancvue.rest.client.response

import com.bancvue.rest.envelope.DefaultEnvelope
import com.bancvue.rest.exception.ConflictException
import com.bancvue.rest.exception.DefaultWebApplicationExceptionFactory
import javax.ws.rs.core.GenericType
import javax.ws.rs.core.Response
import org.apache.http.HttpStatus
import spock.lang.Specification
import spock.lang.Unroll

class CreateResponseTest extends Specification {

	Response clientResponse
	CreateResponse postResponse

	void setup() {
		clientResponse = Mock()
		postResponse = new CreateResponse(clientResponse, new DefaultWebApplicationExceptionFactory())
	}

	@Unroll
	def "assertEntityCreatedAndGet with GenericType convert and return #response if status #statusCode"() {
		GenericType<String> genericType = new GenericType<String>() {}
		clientResponse.getStatus() >> statusCode
		if (response) {
			clientResponse.readEntity(genericType) >> response
		}

		expect:
		response == postResponse.getValidatedResponse(genericType)

		where:
		statusCode               | response
		HttpStatus.SC_CREATED    | "value"
		HttpStatus.SC_OK         | "value"
		HttpStatus.SC_NO_CONTENT | null
	}

	def "assertEntityCreatedAndGetResponse should throw ConflictException when server returns 409 with no entity"() {
		given:
		GenericType<String> genericType = new GenericType<String>() {}
		clientResponse.getStatus() >> HttpStatus.SC_CONFLICT
		clientResponse.readEntity(_) >> null

		when:
		postResponse.getValidatedResponse(genericType)

		then:
		thrown(ConflictException)
	}

	def "assertEntityCreatedAndGetResponse should throw ConflictException when server returns 409 with envelope with no entity"() {
		given:
		GenericType<String> genericType = new GenericType<String>() {}
		clientResponse.getStatus() >> HttpStatus.SC_CONFLICT
		clientResponse.readEntity(genericType) >> new DefaultEnvelope();

		when:
		postResponse.getValidatedResponse(genericType)

		then:
		thrown(ConflictException)
	}

}
