package com.bancvue.rest

import com.bancvue.rest.client.ClientResponseFactory
import com.bancvue.rest.client.GetResponse
import com.bancvue.rest.example.Widget
import com.bancvue.rest.example.WidgetServiceRule
import com.bancvue.rest.exception.HttpClientException
import com.sun.jersey.api.client.WebResource
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

class GetClientResponseCorrespondsToServerResponseSpecification extends Specification {

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
		Widget expectedWidget = addWidget("wid")

		when:
		GetResponse getResponse = clientResponseFactory.get(widgetResource.path("wid"))

		then:
		getResponse.clientResponse.getStatus() == 200
		getResponse.clientResponse.getLocation() as String == "http://localhost:8080/widgets/wid"

		when:
		Widget actualWidget = getResponse.getResponseAsTypeOrNull(Widget)

		then:
		expectedWidget == actualWidget
		!expectedWidget.is(actualWidget)
	}

	def "not found should return status code 404 and location, client response should convert to null"() {
		when:
		GetResponse getResponse = clientResponseFactory.get(widgetResource.path("wid"))

		then:
		getResponse.clientResponse.getStatus() == 404
		getResponse.clientResponse.getLocation() as String == "http://localhost:8080/widgets/wid"

		when:
		Widget actualWidget = getResponse.getResponseAsTypeOrNull(Widget)

		then:
		actualWidget == null
	}

	def "application error should return status code 500, client response should convert to http exception"() {
		Widget expectedWidget = addWidget("wid")
		expectedWidget.initApplicationError()

		when:
		GetResponse getResponse = clientResponseFactory.get(widgetResource.path("wid"))

		then:
		getResponse.clientResponse.getStatus() == 500
		getResponse.clientResponse.getLocation() == null

		when:
		getResponse.getResponseAsTypeOrNull(Widget)

		then:
		HttpClientException ex = thrown(HttpClientException)
		ex.getStatus() == 500
	}

}
