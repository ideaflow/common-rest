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
