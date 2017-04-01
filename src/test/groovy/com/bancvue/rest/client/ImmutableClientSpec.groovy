/*
 * Copyright 2014 BancVue, LTD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bancvue.rest.client

import com.bancvue.rest.exception.mapper.InvalidEntityExceptionMapper
import javax.ws.rs.client.WebTarget
import org.glassfish.jersey.client.ClientProperties
import spock.lang.Specification

class ImmutableClientSpec extends Specification {

	ImmutableClient original = new ImmutableClient()

	def "should not modify original client when setting a property"() {
		when:
		ImmutableClient newClient = original.property(ClientProperties.CONNECT_TIMEOUT, 1000)

		then:
		newClient.configuration.getProperty(ClientProperties.CONNECT_TIMEOUT) == 1000
		original.configuration.getProperty(ClientProperties.CONNECT_TIMEOUT) == null
	}

	def "should not modify original client when registering a component"() {
		when:
		ImmutableClient newClient = original.register(InvalidEntityExceptionMapper)

		then:
		newClient.configuration.classes == [InvalidEntityExceptionMapper] as Set
		original.configuration.classes == [] as Set
	}

	def "should create web target using current state"() {
		when:
		WebTarget target = original.property(ClientProperties.CONNECT_TIMEOUT, 1000)
			.target("http://localhost:8080")

		then:
		target.configuration.getProperty(ClientProperties.CONNECT_TIMEOUT) == 1000
		target.getUri().getScheme() == "http"
		target.getUri().getHost() == "localhost"
		target.getUri().getPort() == 8080
	}

def "should fail"() {
expect:
false
}
}
