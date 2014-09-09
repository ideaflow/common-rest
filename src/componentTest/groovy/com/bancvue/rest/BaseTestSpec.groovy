package com.bancvue.rest

import com.bancvue.rest.client.BasicClientRequest
import com.bancvue.rest.client.ClientRequest
import com.bancvue.rest.example.WidgetRepository
import com.bancvue.rest.example.WidgetService
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.WebTarget
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Shared
import spock.lang.Specification

@IntegrationTest
@WebAppConfiguration
@ContextConfiguration(classes = WidgetService, loader = SpringApplicationContextLoader)
abstract class BaseTestSpec extends Specification {

	@Autowired
	WidgetRepository widgetRepository

	@Shared
	ClientRequest clientRequest

	def setupSpec() {
		Client jerseyClient = ClientBuilder.newClient()
		WebTarget baseServiceResource = jerseyClient.target("http://localhost:8080/")
		clientRequest = new BasicClientRequest(baseServiceResource).path("widgets")
	}

	def setup() {
		widgetRepository.clear()
	}

}
