package com.bancvue.rest.client

import com.bancvue.rest.HttpClientException
import com.sun.jersey.api.client.ClientResponse
import org.apache.http.HttpStatus
import spock.lang.Specification

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class GetResponseTest extends Specification {

	def "acquireResponseAsType should return entity from response"() {
		ClientResponse clientResponse = mock(ClientResponse)
		GetResponse getResponse = new GetResponse(clientResponse)
		when(clientResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
		when(clientResponse.getEntity(String)).thenReturn("value")

		when:
		String entity = getResponse.acquireResponseAsType(String)

		then:
		assert entity == "value"
	}

	def "acquireResponseAsType should throw runtime exception if status not found"() {
		ClientResponse clientResponse = mock(ClientResponse)
		GetResponse getResponse = new GetResponse(clientResponse)
		when(clientResponse.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);

		when:
		getResponse.acquireResponseAsType(Object)

		then:
		HttpClientException ex = thrown(HttpClientException)
		ex.status == HttpStatus.SC_NOT_FOUND
	}

}
