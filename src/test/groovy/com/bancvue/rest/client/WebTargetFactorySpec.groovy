package com.bancvue.rest.client

import javax.ws.rs.client.WebTarget
import spock.lang.Specification

class WebTargetFactorySpec extends Specification {

	private WebTargetFactory factory = new WebTargetFactory()

	def "should throw SchemeRequiredException if uri has no scheme"() {
		when:
		factory.create("localhost:8080")

		then:
		thrown(WebTargetFactory.MalformedUriException)
	}

	def "should return WebTarget"() {
		when:
		WebTarget target = factory.create("http://localhost:8080")

		then:
		assert target.getUri().getScheme() == "http"
		assert target.getUri().getPort() == 8080
		assert target.getUri().getHost() == "localhost"
	}

}
