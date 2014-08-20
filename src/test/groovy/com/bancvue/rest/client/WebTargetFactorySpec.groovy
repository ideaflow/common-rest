package com.bancvue.rest.client

import javax.ws.rs.client.WebTarget
import org.glassfish.jersey.client.ClientConfig
import org.glassfish.jersey.client.ClientProperties
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
	
	def "should return WebTarget with configuration"() {
		when:
		ClientConfig config = new ClientConfig().property(ClientProperties.CONNECT_TIMEOUT, 1000)
		WebTargetFactory configuredFactor = new WebTargetFactory(config)
		WebTarget target = configuredFactor.create("http://localhost:8080")

		then:
		target.config.state.commonConfig.properties.get(ClientProperties.CONNECT_TIMEOUT) == 1000
	}

}
