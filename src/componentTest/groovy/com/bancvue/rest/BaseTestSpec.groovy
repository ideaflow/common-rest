package com.bancvue.rest

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

	@Shared
	Client jerseyClient

	@Shared
	WebTarget baseServiceResource

	@Autowired
	WidgetRepository widgetRepository


	public setupSpec() {
		jerseyClient = ClientBuilder.newClient()
		baseServiceResource = jerseyClient.target("http://localhost:8080/")
	}
}
