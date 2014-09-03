package com.bancvue.rest.client.response

import com.bancvue.rest.Envelope
import com.bancvue.rest.exception.ConflictException
import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory
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
		postResponse = new CreateResponse(clientResponse, new UnexpectedResponseExceptionFactory.Default())
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

	def "assertEntityCreatedAndGetResponse should throw ConflictException when server returns 409 with no entity" (){
		given:
		GenericType<String> genericType = new GenericType<String>() {}
		clientResponse.getStatus() >> HttpStatus.SC_CONFLICT
		clientResponse.readEntity(_) >> null

		when:
		postResponse.getValidatedResponse(genericType)

		then:
		thrown(ConflictException)
	}

	def "assertEntityCreatedAndGetResponse should throw ConflictException when server returns 409 with envelope with no entity" (){
		given:
		GenericType<String> genericType = new GenericType<String>() {}
		clientResponse.getStatus() >> HttpStatus.SC_CONFLICT
		clientResponse.readEntity(genericType) >> new Envelope();

		when:
		postResponse.getValidatedResponse(genericType)

		then:
		thrown(ConflictException)
	}

}
