package com.bancvue.rest

import com.bancvue.rest.client.BasicClientRequestExecutor
import com.bancvue.rest.client.ClientRequestExecutor
import com.bancvue.rest.client.response.DeleteResponse
import com.bancvue.rest.example.Widget
import com.bancvue.rest.exception.NotFoundException
import javax.ws.rs.WebApplicationException
import javax.ws.rs.client.WebTarget
import spock.lang.Shared

class DeleteClientResponseCorrespondsToServerResponseSpecification extends BaseTestSpec {

	@Shared
	private WebTarget widgetResource

	private ClientRequestExecutor clientRequestExecutor

	void setup() {
		widgetResource = baseServiceResource.path("widgets")
		clientRequestExecutor = new BasicClientRequestExecutor(widgetResource)
		widgetRepository.clear()
	}

	private Widget addWidget(String id) {
		Widget widget = new Widget(id: id)
		widgetRepository.put(id, widget)
		widget
	}

	def "success should return status code 204, client response should return null"() {
		Widget widget = addWidget("to-delete")

		when:
		DeleteResponse deleteResponse = clientRequestExecutor.delete(widgetResource.path(widget.id))

		then:
		deleteResponse.clientResponse.getStatus() == 204

		when:
		Widget deletedWidget = deleteResponse.getValidatedResponse(Widget)

		then:
		deletedWidget == null
	}

	def "object not found should return status code 404, client response should throw exception"() {
		when:
		DeleteResponse deleteResponse = clientRequestExecutor.delete(widgetResource.path("not-found"))

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
		DeleteResponse deleteResponse = clientRequestExecutor.delete(widgetResource.path("app-error"))

		then:
		deleteResponse.clientResponse.getStatus() == 500

		when:
		deleteResponse.getValidatedResponse(Widget)

		then:
		WebApplicationException ex = thrown()
		ex.response.status == 500
	}
}
