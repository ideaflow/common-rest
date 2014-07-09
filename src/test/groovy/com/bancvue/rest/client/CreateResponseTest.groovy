package com.bancvue.rest.client

import com.bancvue.rest.Envelope
import com.bancvue.rest.exception.ConflictException
import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory
import org.apache.http.HttpStatus

import spock.lang.Specification

import javax.ws.rs.core.GenericType
import javax.ws.rs.core.Response

class CreateResponseTest extends Specification {

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

	def "assertEntityCreatedAndGetResponse should throw ConflictException when server returns 409 with no entity" (){
		given:
		GenericType<String> genericType = new GenericType<String>() {}
		clientResponse.getStatus() >> HttpStatus.SC_CONFLICT
		clientResponse.readEntity(_) >> null

		when:
		postResponse.assertEntityCreatedAndGetResponse(genericType)

		then:
		thrown(ConflictException)
	}

	def "assertEntityCreatedAndGetResponse should throw ConflictException when server returns 409 with envelope with no entity" (){
		given:
		GenericType<String> genericType = new GenericType<String>() {}
		clientResponse.getStatus() >> HttpStatus.SC_CONFLICT
		clientResponse.readEntity(genericType) >> new Envelope();

		when:
		postResponse.assertEntityCreatedAndGetResponse(genericType)

		then:
		thrown(ConflictException)
	}

}
