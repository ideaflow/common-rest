package com.bancvue.rest

import com.bancvue.rest.client.ClientResponseFactory
import com.bancvue.rest.client.DeleteResponse
import com.bancvue.rest.example.Widget
import com.bancvue.rest.example.WidgetServiceRule
import com.bancvue.rest.exception.HttpClientException
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

class DeleteSpecification extends Specification {

	@Shared
	@ClassRule
	WidgetServiceRule widgetRule = WidgetServiceRule.create()
	private WebResource widgetResource
	private ClientResponseFactory clientResponseFactory

	void setup() {
		widgetResource = widgetRule.baseServiceResource.path("widgets")
		clientResponseFactory = new ClientResponseFactory()
		widgetRule.widgets.clear()
	}

	private Widget addWidget(String id) {
		Widget widget = new Widget(id: id)
		widgetRule.widgets.put(id, widget)
		widget
	}

	def "success should return status code 200 and location, client response should convert and return deleted entity"() {
		Widget widget = addWidget("to-delete")

		when:
		DeleteResponse deleteResponse = clientResponseFactory.delete(widgetResource.path(widget.id))

		then:
		assert deleteResponse.response.getStatus() == 200
		assert deleteResponse.response.getLocation() as String == "http://localhost:8080/widgets/to-delete"

		when:
		Widget deletedWidget = deleteResponse.assertEntityDeletedAndGet(Widget)

		then:
		assert widget == deletedWidget
		assert !widget.is(deletedWidget)
	}

	def "success with no returned entity should return status code 204 and location, client response should return null"() {
		Widget widget = addWidget("to-delete")
		widget.deletedItemNotIncludedInResultBody = true

		when:
		DeleteResponse deleteResponse = clientResponseFactory.delete(widgetResource.path(widget.id))

		then:
		assert deleteResponse.response.getStatus() == 204
		assert deleteResponse.response.getLocation() as String == "http://localhost:8080/widgets/to-delete"

		when:
		Widget deletedWidget = deleteResponse.assertEntityDeletedAndGet(Widget)

		then:
		assert deletedWidget == null
	}

	def "object not found should return status code 404 and location, client response should throw exception"() {
		when:
		DeleteResponse deleteResponse = clientResponseFactory.delete(widgetResource.path("not-found"))

		then:
		assert deleteResponse.response.getStatus() == 404
		assert deleteResponse.response.getLocation() as String == "http://localhost:8080/widgets/not-found"

		when:
		deleteResponse.assertEntityDeletedAndGet(Widget)

		then:
		HttpClientException ex = thrown(HttpClientException)
		assert ex.status == 404
	}

	def "application error should return status code 500, client response should convert to http exception"() {
		Widget widget = addWidget("app-error")
		widget.initApplicationError()

		when:
		DeleteResponse deleteResponse = clientResponseFactory.delete(widgetResource.path("app-error"))

		then:
		assert deleteResponse.response.getStatus() == 500
		assert deleteResponse.response.getLocation() == null

		when:
		deleteResponse.assertEntityDeletedAndGet(Widget)

		then:
		HttpClientException ex = thrown(HttpClientException)
		assert ex.getStatus() == 500
	}
}
