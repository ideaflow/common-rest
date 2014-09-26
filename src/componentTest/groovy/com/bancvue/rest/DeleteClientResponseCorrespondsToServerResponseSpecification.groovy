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

import com.bancvue.rest.client.response.DeleteResponse
import com.bancvue.rest.example.Widget
import com.bancvue.rest.exception.NotFoundException
import javax.ws.rs.WebApplicationException

class DeleteClientResponseCorrespondsToServerResponseSpecification extends BaseTestSpec {

	private Widget addWidget(String id) {
		Widget widget = new Widget(id: id)
		widgetRepository.put(id, widget)
		widget
	}

	def "success should return status code 204, client response should return null"() {
		Widget widget = addWidget("to-delete")

		when:
		DeleteResponse deleteResponse = clientRequest.path(widget.id).delete()

		then:
		deleteResponse.clientResponse.getStatus() == 204

		when:
		Widget deletedWidget = deleteResponse.getValidatedResponse(Widget)

		then:
		deletedWidget == null
	}

	def "object not found should return status code 404, client response should throw exception"() {
		when:
		DeleteResponse deleteResponse = clientRequest.path("not-found").delete()

		then:
		deleteResponse.clientResponse.getStatus() == 404

		when:
		deleteResponse.getValidatedResponse(Widget)

		then:
		thrown NotFoundException
	}

	def "application error should return status code 500, client response should convert to http exception"() {
		Widget widget = addWidget("app-error")
		widget.initApplicationError()

		when:
		DeleteResponse deleteResponse = clientRequest.path("app-error").delete()

		then:
		deleteResponse.clientResponse.getStatus() == 500

		when:
		deleteResponse.getValidatedResponse(Widget)

		then:
		WebApplicationException ex = thrown()
		ex.response.status == 500
	}
}
