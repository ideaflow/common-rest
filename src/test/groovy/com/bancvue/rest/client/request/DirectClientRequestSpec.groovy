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
package com.bancvue.rest.client.request

import com.bancvue.rest.client.ImmutableClient
import javax.ws.rs.client.WebTarget
import spock.lang.Specification

class DirectClientRequestSpec extends Specification {

	ImmutableClient client = ImmutableClient.createDefault()

	def "should throw SchemeRequiredException if uri has no scheme"() {
		when:
		new DirectClientRequest(client, "localhost:8080")

		then:
		thrown(DirectClientRequest.MalformedUriException)
	}

	def "should initialize delegate with constructed WebTarget"() {
		when:
		DirectClientRequest request = new DirectClientRequest(client, "http://localhost:8080")

		then:
		WebTarget target = request.delegate.resource
		assert target.getUri().getScheme() == "http"
		assert target.getUri().getPort() == 8080
		assert target.getUri().getHost() == "localhost"
	}

}
