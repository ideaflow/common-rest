package com.bancvue.rest

import com.bancvue.rest.client.ClientResponseFactory
import com.bancvue.rest.client.UpdateResponse
import com.bancvue.rest.example.Widget
import com.bancvue.rest.example.WidgetServiceRule
import com.bancvue.rest.exception.HttpClientException
import com.sun.jersey.api.client.WebResource
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

class UpdateSpecification extends Specification {

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

	def "success should return status code 204 and location"() {
		Widget update = addWidget("updated")

		when:
		UpdateResponse updateResponse = clientResponseFactory.update(widgetResource.path(update.id), update)

		then:
		assert updateResponse.response.getStatus() == 204
		assert updateResponse.response.getLocation() as String == "http://localhost:8080/widgets/updated"

		when:
		updateResponse.assertResponseSuccess()

		then:
		notThrown(Throwable)
	}

	// TODO: what behavior do we want around PUT?
	// do we allow create?

	def "application error should return status code 500, client response should convert to http exception"() {
		Widget widget = addWidget("updated")
		widget.initApplicationError()

		when:
		UpdateResponse updateResponse = clientResponseFactory.update(widgetResource.path(widget.id), widget)

		then:
		assert updateResponse.response.getStatus() == 500
		assert updateResponse.response.getLocation() == null

		when:
		updateResponse.assertResponseSuccess()

		then:
		HttpClientException ex = thrown(HttpClientException)
		assert ex.status == 500
	}

}
