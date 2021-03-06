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
import com.bancvue.rest.exception.SeeOtherException
import javax.ws.rs.core.GenericType
import javax.ws.rs.core.MultivaluedHashMap
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.core.Response
import org.apache.http.HttpStatus
import spock.lang.Specification

class GetResponseTest extends Specification {

	Response clientResponse
	GetResponse getResponse

	void setup() {
		clientResponse = Mock()
		getResponse = new GetResponse(clientResponse, new DefaultWebApplicationExceptionFactory())
	}

	def "acquireResponseAsType should return entity from response if status ok"() {
		clientResponse.getStatus() >> HttpStatus.SC_OK
		clientResponse.readEntity(String) >> "value"

		when:
		String entity = getResponse.getValidatedResponse(String)

		then:
		"value" == entity
	}

	def "acquireResponseAsType should throw runtime exception if status not found"() {
		clientResponse.getStatus() >> HttpStatus.SC_NOT_FOUND

		when:
		getResponse.getValidatedResponse(Object)

		then:
		thrown NotFoundException
	}

	def "acquireResponseAsType should throw SeeOther exception if status is 303"() {
		clientResponse.getStatus() >> HttpStatus.SC_SEE_OTHER
		MultivaluedMap<String, List<String>> map = new MultivaluedHashMap<String, List<String>>();
		map.put("Location", ["/see/other"])
		clientResponse.getHeaders() >> map

		when:
		getResponse.getValidatedResponse(Object)

		then:
		SeeOtherException ex = thrown(SeeOtherException)
		ex.otherLocation == "/see/other"
	}

	def "getResponseAsType with GenericType should return entity from response if status ok"() {
		GenericType<String> genericType = new GenericType<String>() {
		}
		clientResponse.getStatus() >> HttpStatus.SC_OK
		clientResponse.readEntity(genericType) >> "value"

		when:
		String entity = getResponse.getValidatedResponse(genericType)

		then:
		"value" == entity
	}

	def "acquireResponseAsType with GenericType should return entity fromr esponse if status ok"() {
		GenericType<String> genericType = new GenericType<String>() {
		}
		clientResponse.getStatus() >> HttpStatus.SC_OK
		clientResponse.readEntity(genericType) >> "value"

		when:
		String entity = getResponse.getValidatedResponse(genericType)

		then:
		"value" == entity
	}

	def "getResponseAsType should throw SeeOther exception if status is 303"() {
		clientResponse.getStatus() >> HttpStatus.SC_SEE_OTHER
		MultivaluedMap<String, List<String>> map = new MultivaluedHashMap<String, List<String>>();
		map.put("Location", ["/see/other"])
		clientResponse.getHeaders() >> map

		given:
		GenericType<String> genericType = new GenericType<String>() {
		}

		when:
		getResponse.getValidatedResponse(genericType)

		then:
		SeeOtherException ex = thrown(SeeOtherException)
		ex.otherLocation == "/see/other"
	}
}
