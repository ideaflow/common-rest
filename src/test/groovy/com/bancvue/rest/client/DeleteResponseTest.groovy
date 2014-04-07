package com.bancvue.rest.client

import com.bancvue.rest.exception.NotFoundException
import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory
import com.sun.jersey.api.client.ClientResponse
import com.sun.jersey.api.client.GenericType
import org.apache.http.HttpStatus
import spock.lang.Specification


class DeleteResponseTest extends Specification {

	ClientResponse clientResponse
	DeleteResponse deleteResponse

	void setup() {
		clientResponse = Mock()
		deleteResponse = new DeleteResponse(clientResponse, new UnexpectedResponseExceptionFactory.Default())
	}

	def "assertEntityDeletedAndGet with GenericType convert and return entity if status ok"() {
		GenericType<String> genericType = new GenericType<String>() {}
		clientResponse.getStatus() >> HttpStatus.SC_OK
		clientResponse.getEntity(genericType) >> "value"

		when:
		String actualResponse = deleteResponse.assertEntityDeletedAndGetResponse(genericType)

		then:
		"value" == actualResponse
	}

    def "Should throw NotFoundException if status is 404"() {
        clientResponse.getStatus() >> HttpStatus.SC_NOT_FOUND

        when:
        deleteResponse.assertEntityDeletedAndGetResponse(String.class)

        then:
        thrown NotFoundException
    }

}
