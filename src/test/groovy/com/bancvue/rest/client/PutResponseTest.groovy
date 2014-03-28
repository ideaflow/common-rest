package com.bancvue.rest.client

import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory
import com.sun.jersey.api.client.ClientResponse
import com.sun.jersey.api.client.GenericType
import org.apache.http.HttpStatus
import spock.lang.Specification


class PutResponseTest extends Specification {
	
	ClientResponse clientResponse
	UpdateResponse putResponse

	void setup() {
		clientResponse = Mock()
		putResponse = new UpdateResponse(clientResponse, new UnexpectedResponseExceptionFactory.Default())
	}

	def "assertEntityUpdatedAndGetEntity with GenericType should convert and return entity if status ok"() {
		GenericType<String> genericType = new GenericType<String>() {}
		clientResponse.getStatus() >> HttpStatus.SC_OK
		clientResponse.getEntity(genericType) >> "value"

		when:
		String actualResponse = putResponse.assertEntityUpdatedAndGetResponse(genericType)

		then:
		"value" == actualResponse
	}
	
}
