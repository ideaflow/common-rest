package com.bancvue.rest.client.request

import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Invocation
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.Cookie
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType
import org.glassfish.jersey.client.ClientConfig
import spock.lang.Specification

class RequestParamsSpec extends Specification {

	RequestParams original = new RequestParams()

	def "request should add request types and not change original"() {
		when:
		RequestParams modified = original.request(MediaType.APPLICATION_ATOM_XML_TYPE)

		then:
		original.requestTypes == []
		modified.requestTypes == [MediaType.APPLICATION_ATOM_XML_TYPE]
	}

	def "accept should add accept types and not change original"() {
		when:
		RequestParams modified = original.accept(MediaType.APPLICATION_ATOM_XML_TYPE)

		then:
		original.acceptTypes == []
		modified.acceptTypes == [MediaType.APPLICATION_ATOM_XML_TYPE]
	}

	def "header should add header and not change original"() {
		when:
		RequestParams modified = original.header("header-key", "header-value")

		then:
		original.headers.isEmpty()
		modified.headers.get("header-key") == ["header-value"]
	}

	def "property should add property and not change original"() {
		when:
		RequestParams modified = original.property("property-key", "property-value")

		then:
		original.@properties.isEmpty()
		modified.@properties.get("property-key") == "property-value"
	}

	def "cookie should add cookie and not change original"() {
		when:
		Cookie cookie = new Cookie("key", "value")
		RequestParams modified = original.cookie(cookie)

		then:
		original.cookies.isEmpty()
		modified.cookies == [cookie]
	}

	def "createInvocation should return builder with request parameters"() {
		given:
		Cookie cookie = new Cookie("key", "value")
		WebTarget webTarget = ClientBuilder.newClient(new ClientConfig()).target("http://localhost:8080")
		RequestParams params = original.request(MediaType.TEXT_HTML_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_XML_TYPE)
				.property("property-key", "property-value")
				.header("header-key", "header-value")
				.cookie(cookie)

		when:
		Invocation.Builder builder = params.createInvocation(webTarget)
		org.glassfish.jersey.client.ClientRequest request = builder.buildPost(null).requestContext

		then:
		request.getHeaders().get(HttpHeaders.ACCEPT) == [MediaType.TEXT_HTML_TYPE, MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_XML_TYPE]
		request.getProperty("property-key") == "property-value"
		request.getHeaderString("header-key") == "header-value"
		request.getCookies() == ["key": cookie]
	}

}
