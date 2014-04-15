package com.bancvue.rest.client

import spock.lang.Specification

import javax.ws.rs.core.GenericType
import javax.ws.rs.core.Response

class EntityResolverTest extends Specification {

	Response response = Mock()

	def "class resolver should retrieve entity with Class"() {
		Object expectedResult = new Object()

		when:
		Object actualResult = EntityResolver.CLASS_RESOLVER.getEntity(response, Object)

		then:
		1 * response.readEntity(Object) >> expectedResult
		expectedResult == actualResult
	}

	def "generic type resolver should retrieve entity with GenericType"() {
		Object expectedResult = new Object()
		GenericType<Object> genericType = new GenericType<Object>() {}

		when:
		Object actualResult = EntityResolver.GENERIC_TYPE_RESOLVER.getEntity(response, genericType)

		then:
		1 * response.readEntity(genericType) >> expectedResult
		expectedResult == actualResult
	}

}
