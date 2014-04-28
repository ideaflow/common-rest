package com.bancvue.rest
import com.bancvue.rest.client.ClientResponseFactory
import com.bancvue.rest.client.DeleteResponse
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

	def "success should return status code 200 and location, client response should convert and return deleted entity"() {
		Widget widget = addWidget("to-delete")

		when:
		DeleteResponse deleteResponse = clientResponseFactory.delete(widgetResource.path(widget.id))

		then:
		deleteResponse.clientResponse.getStatus() == 200
		deleteResponse.clientResponse.getLocation() as String == "http://localhost:8080/widgets/to-delete"

		when:
		Widget deletedWidget = deleteResponse.assertEntityDeletedAndGetResponse(Widget)

		then:
		widget == deletedWidget
		!widget.is(deletedWidget)
	}

	def "success with no returned entity should return status code 204 and location, client response should return null"() {
		Widget widget = addWidget("to-delete")
		widget.deletedItemNotIncludedInResultBody = true

		when:
		DeleteResponse deleteResponse = clientResponseFactory.delete(widgetResource.path(widget.id))

		then:
		deleteResponse.clientResponse.getStatus() == 204
		deleteResponse.clientResponse.getLocation() as String == "http://localhost:8080/widgets/to-delete"

		when:
		Widget deletedWidget = deleteResponse.assertEntityDeletedAndGetResponse(Widget)

		then:
		deletedWidget == null
	}

	def "object not found should return status code 404 and location, client response should throw exception"() {
		when:
		DeleteResponse deleteResponse = clientResponseFactory.delete(widgetResource.path("not-found"))

		then:
		deleteResponse.clientResponse.getStatus() == 404
		deleteResponse.clientResponse.getLocation() as String == "http://localhost:8080/widgets/not-found"

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
		deleteResponse.clientResponse.getLocation() == null

		when:
		deleteResponse.assertEntityDeletedAndGetResponse(Widget)

		then:
		HttpClientException ex = thrown(HttpClientException)
		ex.getStatus() == 500
	}
}
