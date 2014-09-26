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

import com.bancvue.rest.client.response.GetResponse
import com.bancvue.rest.example.Widget
import com.bancvue.rest.exception.NotFoundException
import javax.ws.rs.WebApplicationException

class GetClientResponseCorrespondsToServerResponseSpecification extends BaseTestSpec {

	private Widget addWidget(String id) {
		Widget widget = new Widget(id: id)
		widgetRepository.put(id, widget)
		widget
	}

	def "success should return status code 200, client response should convert and return entity"() {
		Widget expectedWidget = addWidget("wid")

		when:
		GetResponse getResponse = clientRequest.path("wid").get()

		then:
		getResponse.clientResponse.getStatus() == 200

		when:
		Widget actualWidget = getResponse.getValidatedResponse(Widget)

		then:
		expectedWidget == actualWidget
		!expectedWidget.is(actualWidget)
	}

	def "should throw not found exception when return status code 404"() {
		when:
		GetResponse getResponse = clientRequest.path("wid").get()

		then:
		getResponse.clientResponse.getStatus() == 404

		when:
		getResponse.getValidatedResponse(Widget)

		then:
		thrown NotFoundException
	}

	def "application error should return status code 500, client response should convert to http exception"() {
		Widget expectedWidget = addWidget("wid")
		expectedWidget.initApplicationError()

		when:
		GetResponse getResponse = clientRequest.path("wid").get()

		then:
		getResponse.clientResponse.getStatus() == 500

		when:
		getResponse.getValidatedResponse(Widget)

		then:
		WebApplicationException ex = thrown()
		ex.response.status == 500
	}
}
