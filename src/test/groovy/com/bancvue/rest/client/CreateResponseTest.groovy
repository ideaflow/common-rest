package com.bancvue.rest.client

import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory
import com.sun.jersey.api.client.ClientResponse
import com.sun.jersey.api.client.GenericType
import org.apache.http.HttpStatus
import spock.lang.Specification


class CreateResponseTest extends Specification {

	ClientResponse clientResponse
	CreateResponse createResponse

	void setup() {
		clientResponse = Mock()
		createResponse = new CreateResponse(clientResponse, new UnexpectedResponseExceptionFactory.Default())
	}

	def "assertEntityCreatedAndGet with GenericType convert and return entity if status created"() {
		GenericType<String> genericType = new GenericType<String>() {}
		clientResponse.getStatus() >> HttpStatus.SC_CREATED
		clientResponse.getEntity(genericType) >> "value"

		when:
		String actualResponse = createResponse.assertEntityCreatedAndGet(genericType)

		then:
		"value" == actualResponse
	}

}
