package com.bancvue.rest.client

import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory
import org.apache.http.HttpStatus

import spock.lang.Specification

import javax.ws.rs.core.GenericType
import javax.ws.rs.core.Response

class PostResponseTest extends Specification {

	Response clientResponse
	CreateResponse postResponse

	void setup() {
		clientResponse = Mock()
		postResponse = new CreateResponse(clientResponse, new UnexpectedResponseExceptionFactory.Default())
	}

	def "assertEntityCreatedAndGet with GenericType convert and return entity if status created"() {
		GenericType<String> genericType = new GenericType<String>() {}
		clientResponse.getStatus() >> HttpStatus.SC_CREATED
		clientResponse.readEntity(genericType) >> "value"

		when:
		String actualResponse = postResponse.assertEntityCreatedAndGetResponse(genericType)

		then:
		"value" == actualResponse
	}

}
