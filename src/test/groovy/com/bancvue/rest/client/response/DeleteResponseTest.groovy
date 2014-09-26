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

import com.bancvue.rest.exception.DefaultWebApplicationExceptionFactory
import com.bancvue.rest.exception.NotFoundException
import javax.ws.rs.core.GenericType
import javax.ws.rs.core.Response
import org.apache.http.HttpStatus
import spock.lang.Specification

class DeleteResponseTest extends Specification {

	Response clientResponse
	DeleteResponse deleteResponse

	void setup() {
		clientResponse = Mock()
		deleteResponse = new DeleteResponse(clientResponse, new DefaultWebApplicationExceptionFactory())
	}

	def "assertEntityDeletedAndGet with GenericType convert and return entity if status ok"() {
		GenericType<String> genericType = new GenericType<String>() {}
		clientResponse.getStatus() >> HttpStatus.SC_OK
		clientResponse.readEntity(genericType) >> "value"

		when:
		String actualResponse = deleteResponse.getValidatedResponse(genericType)

		then:
		"value" == actualResponse
	}

	def "Should throw NotFoundException if status is 404"() {
		clientResponse.getStatus() >> HttpStatus.SC_NOT_FOUND

		when:
		deleteResponse.getValidatedResponse(String.class)

		then:
		thrown NotFoundException
	}

}
