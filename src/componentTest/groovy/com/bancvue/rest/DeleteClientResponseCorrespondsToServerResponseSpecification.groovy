package com.bancvue.rest
import com.bancvue.rest.client.ClientResponseFactory
import com.bancvue.rest.client.response.DeleteResponse
import com.bancvue.rest.example.Widget
import com.bancvue.rest.exception.HttpClientException
import spock.lang.Shared

import javax.ws.rs.client.WebTarget

class DeleteClientResponseCorrespondsToServerResponseSpecification extends BaseTestSpec {

	@Shared
	private WebTarget widgetResource

	private ClientResponseFactory clientResponseFactory

	void setup() {
		widgetResource = baseServiceResource.path("widgets")
		clientResponseFactory = new ClientResponseFactory()
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
		DeleteResponse deleteResponse = clientResponseFactory.delete(widgetResource.path(widget.id))

		then:
		deleteResponse.clientResponse.getStatus() == 204

		when:
		Widget deletedWidget = deleteResponse.assertEntityDeletedAndGetResponse(Widget)

		then:
		deletedWidget == null
	}

	def "object not found should return status code 404, client response should throw exception"() {
		when:
		DeleteResponse deleteResponse = clientResponseFactory.delete(widgetResource.path("not-found"))

		then:
		deleteResponse.clientResponse.getStatus() == 404

		when:
		deleteResponse.assertEntityDeletedAndGetResponse(Widget)

		then:
		HttpClientException ex = thrown(HttpClientException)
		ex.status == 404
	}

	def "application error should return status code 500, client response should convert to http exception"() {
		Widget widget = addWidget("app-error")
		widget.initApplicationError()

		when:
		DeleteResponse deleteResponse = clientResponseFactory.delete(widgetResource.path("app-error"))

		then:
		deleteResponse.clientResponse.getStatus() == 500

		when:
		deleteResponse.assertEntityDeletedAndGetResponse(Widget)

		then:
		HttpClientException ex = thrown(HttpClientException)
		ex.getStatus() == 500
	}
}
