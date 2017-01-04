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

import com.bancvue.rest.client.response.CreateResponse
import com.bancvue.rest.example.Widget
import com.bancvue.rest.exception.ConflictException
import com.bancvue.rest.exception.ValidationException
import javax.ws.rs.WebApplicationException

class PostClientResponseCorrespondsToServerResponseSpecification extends BaseTestSpec {

	private Widget addWidget(String id) {
		Widget widget = new Widget(id: id)
		widgetRepository.put(id, widget)
		widget
	}

	def "success should return status code 201 and location, client response should convert and return entity"() {
		Widget widget = new Widget(id: "created")

		when:
		CreateResponse createResponse = clientRequest.createWithPost(widget)

		then:
		createResponse.clientResponse.getStatus() == 201
		createResponse.clientResponse.getLocation() as String == "http://localhost:8080/widgets/created"

		when:
		Widget actualWidget = createResponse.getValidatedResponse(Widget)

		then:
		widget == actualWidget
		!widget.is(actualWidget)
	}

	def "object already exists should return status code 409 with entity, client response should convert to exception"() {
		Widget widget = addWidget("duplicate")

		when:
		CreateResponse createResponse = clientRequest.createWithPost(widget)

		then:
		createResponse.clientResponse.getStatus() == 409

		when:
		createResponse.getValidatedResponse(Widget)

		then:
		ConflictException ex = thrown(ConflictException)
		ex.entity == widget
	}

	def "invalid object should return status code 422, client response should convert to exception"() {
		Widget invalid = new Widget()

		when:
		CreateResponse createResponse = clientRequest.createWithPost(invalid)

		then:
		createResponse.clientResponse.getStatus() == 422

		when:
		createResponse.getValidatedResponse(Widget)

		then:
		thrown ValidationException

		// TODO: what about the body?  can we standardize on reporting invalid objects?
	}

	def "application error should return status code 500, client response should convert to http exception"() {
		Widget widget = new Widget(id: "app-error")
		widget.initApplicationError()

		when:
		CreateResponse createResponse = clientRequest.createWithPost(widget)

		then:
		createResponse.clientResponse.getStatus() == 500

		when:
		createResponse.getValidatedResponse(Widget)

		then:
		WebApplicationException ex = thrown()
		ex.response.status == 500
	}
}
