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
package com.bancvue.rest

import com.bancvue.rest.client.request.BasicClientRequest
import com.bancvue.rest.client.request.ClientRequest
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
