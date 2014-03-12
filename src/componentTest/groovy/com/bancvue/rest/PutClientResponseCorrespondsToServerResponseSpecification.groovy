package com.bancvue.rest

import com.bancvue.rest.client.ClientResponseFactory
import com.bancvue.rest.client.PutResponse
import com.bancvue.rest.example.Widget
import com.bancvue.rest.example.WidgetServiceRule
import com.bancvue.rest.exception.HttpClientException
import com.sun.jersey.api.client.WebResource
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

class PutClientResponseCorrespondsToServerResponseSpecification extends Specification {

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

	def "success should return status code 200 and location, client response should convert and return entity"() {
		Widget update = addWidget("updated")

		when:
		PutResponse updateResponse = clientResponseFactory.put(widgetResource.path(update.id), update)

		then:
		updateResponse.clientResponse.getStatus() == 200
		updateResponse.clientResponse.getLocation() as String == "http://localhost:8080/widgets/updated"

		when:
		Widget actualResponse = updateResponse.assertEntityUpdatedAndGetResponse(Widget)

		then:
		update == actualResponse
		!update.is(actualResponse)
	}

	// TODO: what behavior do we want around PUT?
	// do we allow create?

	def "not found should return status code 404 and location, client response should convert to http exception"() {
		Widget widget = new Widget(id: "wid")

		when:
		PutResponse putResponse = clientResponseFactory.put(widgetResource.path("wid"), widget)

		then:
		putResponse.clientResponse.getStatus() == 404
		putResponse.clientResponse.getLocation() as String == "http://localhost:8080/widgets/wid"

		when:
		putResponse.assertEntityUpdatedAndGetResponse(Widget)

		then:
		HttpClientException ex = thrown(HttpClientException)
		ex.status == 404
	}

	def "application error should return status code 500, client response should convert to http exception"() {
		Widget widget = addWidget("updated")
		widget.initApplicationError()

		when:
		PutResponse updateResponse = clientResponseFactory.put(widgetResource.path(widget.id), widget)

		then:
		updateResponse.clientResponse.getStatus() == 500
		updateResponse.clientResponse.getLocation() == null

		when:
		updateResponse.assertEntityUpdatedAndGetResponse(Widget)

		then:
		HttpClientException ex = thrown(HttpClientException)
		ex.status == 500
	}

}