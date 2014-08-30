package com.bancvue.rest.client.response

import com.bancvue.rest.client.response.GetResponse
import com.bancvue.rest.exception.HttpClientException
import com.bancvue.rest.exception.SeeOtherException
import com.bancvue.rest.exception.UnexpectedResponseExceptionFactory
import org.apache.http.HttpStatus
import spock.lang.Specification

import javax.ws.rs.core.GenericType
import javax.ws.rs.core.MultivaluedHashMap
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.core.Response

class GetResponseTest extends Specification {

	Response clientResponse
	GetResponse getResponse

	void setup() {
		clientResponse = Mock()
		getResponse = new GetResponse(clientResponse, new UnexpectedResponseExceptionFactory.Default())
	}

	def "acquireResponseAsType should return entity from response if status ok"() {
		clientResponse.getStatus() >> HttpStatus.SC_OK
		clientResponse.readEntity(String) >> "value"

		when:
		String entity = getResponse.getResponseAsType(String)

		then:
		"value" == entity
	}

	def "acquireResponseAsType should throw runtime exception if status not found"() {
		clientResponse.getStatus() >> HttpStatus.SC_NOT_FOUND

		when:
		getResponse.getResponseAsType(Object)

		then:
		HttpClientException ex = thrown(HttpClientException)
		HttpStatus.SC_NOT_FOUND == ex.status
	}

	def "acquireResponseAsType should throw SeeOther exception if status is 303"() {
		clientResponse.getStatus() >> HttpStatus.SC_SEE_OTHER
		MultivaluedMap<String, List<String>> map = new MultivaluedHashMap<String, List<String>>();
		map.put("Location", ["/see/other"])
		clientResponse.getHeaders() >> map

		when:
		getResponse.getResponseAsType(Object)

		then:
		SeeOtherException ex = thrown(SeeOtherException)
		HttpStatus.SC_SEE_OTHER == ex.status;
		ex.otherLocation == "/see/other"
	}

	def "getResponseAsType with GenericType should return entity from response if status ok"() {
		GenericType<String> genericType = new GenericType<String>() {
				}
		clientResponse.getStatus() >> HttpStatus.SC_OK
		clientResponse.readEntity(genericType) >> "value"

		when:
		String entity = getResponse.getResponseAsType(genericType)

		then:
		"value" == entity
	}

	def "acquireResponseAsType with GenericType should return entity fromr esponse if status ok"() {
		GenericType<String> genericType = new GenericType<String>() {
				}
		clientResponse.getStatus() >> HttpStatus.SC_OK
		clientResponse.readEntity(genericType) >> "value"

		when:
		String entity = getResponse.getResponseAsType(genericType)

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
		getResponse.getResponseAsType(genericType)

		then:
		SeeOtherException ex = thrown(SeeOtherException)
		HttpStatus.SC_SEE_OTHER == ex.status;
		ex.otherLocation == "/see/other"
	}
}
