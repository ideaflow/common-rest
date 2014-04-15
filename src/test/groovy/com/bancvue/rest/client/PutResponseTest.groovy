package com.bancvue.rest.client

import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory
import org.apache.http.HttpStatus
import spock.lang.Specification

import javax.ws.rs.core.GenericType
import javax.ws.rs.core.Response

class PutResponseTest extends Specification {
	
	Response clientResponse
	UpdateResponse putResponse

	void setup() {
		clientResponse = Mock()
		putResponse = new UpdateResponse(clientResponse, new UnexpectedResponseExceptionFactory.Default())
	}

	def "assertEntityUpdatedAndGetEntity with GenericType should convert and return entity if status ok"() {
		GenericType<String> genericType = new GenericType<String>() {}
		clientResponse.getStatus() >> HttpStatus.SC_OK
		clientResponse.readEntity(genericType) >> "value"

		when:
		String actualResponse = putResponse.assertEntityUpdatedAndGetResponse(genericType)

		then:
		"value" == actualResponse
	}
	
}
