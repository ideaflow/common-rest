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

class UpdateResponseTest extends Specification {

	Response clientResponse
	UpdateResponse putResponse

	void setup() {
		clientResponse = Mock()
		putResponse = new UpdateResponse(clientResponse, new DefaultWebApplicationExceptionFactory())
	}

	def "assertEntityUpdatedAndGetEntity with GenericType should convert and return entity if status ok"() {
		given:
		GenericType<String> genericType = new GenericType<String>() {}
		clientResponse.getStatus() >> HttpStatus.SC_OK
		clientResponse.readEntity(genericType) >> "value"

		when:
		String actualResponse = putResponse.getValidatedResponse(genericType)

		then:
		"value" == actualResponse
	}

	def "doAssertEntityUpdatedAndGetResponse should throw ConflictException when server returns 409 with no entity"() {
		given:
		GenericType<String> genericType = new GenericType<String>() {}
		clientResponse.getStatus() >> HttpStatus.SC_CONFLICT
		clientResponse.readEntity(_) >> null

		when:
		putResponse.getValidatedResponse(genericType)

		then:
		thrown(ConflictException)
	}

	def "doAssertEntityUpdatedAndGetResponse should throw ConflictException when server returns 409 with envelope with no entity"() {
		given:
		GenericType<String> genericType = new GenericType<String>() {}
		clientResponse.getStatus() >> HttpStatus.SC_CONFLICT
		clientResponse.readEntity(genericType) >> new DefaultEnvelope();

		when:
		putResponse.getValidatedResponse(genericType)

		then:
		thrown(ConflictException)
	}
}
