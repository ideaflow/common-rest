package com.bancvue.rest.client

import com.bancvue.rest.Envelope
import com.bancvue.rest.exception.ConflictException
import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory
import org.apache.http.HttpStatus
import spock.lang.Specification

import javax.ws.rs.core.GenericType
import javax.ws.rs.core.Response

class UpdateResponseTest extends Specification {
	
	Response clientResponse
	UpdateResponse putResponse

	void setup() {
		clientResponse = Mock()
		putResponse = new UpdateResponse(clientResponse, new UnexpectedResponseExceptionFactory.Default())
	}

	def "assertEntityUpdatedAndGetEntity with GenericType should convert and return entity if status ok"() {
		given:
		GenericType<String> genericType = new GenericType<String>() {}
		clientResponse.getStatus() >> HttpStatus.SC_OK
		clientResponse.readEntity(genericType) >> "value"

		when:
		String actualResponse = putResponse.assertEntityUpdatedAndGetResponse(genericType)

		then:
		"value" == actualResponse
	}

	def "doAssertEntityUpdatedAndGetResponse should throw ConflictException when server returns 409 with no entity" (){
		given:
		GenericType<String> genericType = new GenericType<String>() {}
		clientResponse.getStatus() >> HttpStatus.SC_CONFLICT
		clientResponse.readEntity(_) >> null

		when:
		putResponse.assertEntityUpdatedAndGetResponse(genericType)

		then:
		thrown(ConflictException)
	}

	def "doAssertEntityUpdatedAndGetResponse should throw ConflictException when server returns 409 with envelope with no entity" (){
		given:
		GenericType<String> genericType = new GenericType<String>() {}
		clientResponse.getStatus() >> HttpStatus.SC_CONFLICT
		clientResponse.readEntity(genericType) >> new Envelope();

		when:
		putResponse.assertEntityUpdatedAndGetResponse(genericType)

		then:
		thrown(ConflictException)
	}
}
