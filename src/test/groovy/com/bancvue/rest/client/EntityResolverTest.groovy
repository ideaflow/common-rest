package com.bancvue.rest.client

import com.sun.jersey.api.client.ClientResponse
import com.sun.jersey.api.client.GenericType
import spock.lang.Specification

class EntityResolverTest extends Specification {

	ClientResponse response = Mock()

	def "class resolver should retrieve entity with Class"() {
		Object expectedResult = new Object()

		when:
		Object actualResult = EntityResolver.CLASS_RESOLVER.getEntity(response, Object)

		then:
		1 * response.getEntity(Object) >> expectedResult
		expectedResult == actualResult
	}

	def "generic type resolver should retrieve entity with GenericType"() {
		Object expectedResult = new Object()
		GenericType<Object> genericType = new GenericType<Object>() {}

		when:
		Object actualResult = EntityResolver.GENERIC_TYPE_RESOLVER.getEntity(response, genericType)

		then:
		1 * response.getEntity(genericType) >> expectedResult
		expectedResult == actualResult
	}

}
