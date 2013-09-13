package com.bancvue.rest

import com.bancvue.rest.client.ClientResponseFactory
import com.bancvue.rest.client.GetResponse
import com.bancvue.rest.example.Widget
import com.bancvue.rest.example.WidgetServiceRule
import com.sun.jersey.api.client.WebResource
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification


class GetSpecification extends Specification {

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
		assert getResponse.response.getStatus() == 200
		assert getResponse.response.getLocation() as String == "http://localhost:8080/widgets/wid"

		when:
		Widget actualWidget = getResponse.getResponseAsType(Widget)

		then:
		assert expectedWidget == actualWidget
		assert !expectedWidget.is(actualWidget)
	}

	def "not found should return status code 404 and location, client response should convert to null"() {
		when:
		GetResponse getResponse = clientResponseFactory.get(widgetResource.path("wid"))

		then:
		assert getResponse.response.getStatus() == 404
		assert getResponse.response.getLocation() as String == "http://localhost:8080/widgets/wid"

		when:
		Widget actualWidget = getResponse.getResponseAsType(Widget)

		then:
		assert actualWidget == null
	}

	def "application error should return status code 500, client response should convert to http exception"() {
		Widget expectedWidget = addWidget("wid")
		expectedWidget.codeToEval = "throw new RuntimeException()"

		when:
		GetResponse getResponse = clientResponseFactory.get(widgetResource.path("wid"))

		then:
		assert getResponse.response.getStatus() == 500
		assert getResponse.response.getLocation() == null

		when:
		getResponse.getResponseAsType(Widget)

		then:
		HttpClientException ex = thrown(HttpClientException)
		assert ex.getStatus() == 500
	}

}
