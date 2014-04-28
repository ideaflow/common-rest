package com.bancvue.rest
import com.bancvue.rest.client.ClientResponseFactory
import com.bancvue.rest.client.GetResponse
import com.bancvue.rest.example.Widget
import com.bancvue.rest.exception.HttpClientException
import com.bancvue.rest.exception.NotFoundException
import spock.lang.Shared

import javax.ws.rs.client.WebTarget

class GetClientResponseCorrespondsToServerResponseSpecification extends BaseTestSpec {

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

	def "success should return status code 200 and location, client response should convert and return entity"() {
		Widget expectedWidget = addWidget("wid")

		when:
		GetResponse getResponse = clientResponseFactory.get(widgetResource.path("wid"))

		then:
		getResponse.clientResponse.getStatus() == 200
		getResponse.clientResponse.getLocation() as String == "http://localhost:8080/widgets/wid"

		when:
		Widget actualWidget = getResponse.getResponseAsType(Widget)

		then:
		expectedWidget == actualWidget
		!expectedWidget.is(actualWidget)
	}

	def "should throw not found expect when return status code 404"() {
		when:
		GetResponse getResponse = clientResponseFactory.get(widgetResource.path("wid"))

		then:
		getResponse.clientResponse.getStatus() == 404
		getResponse.clientResponse.getLocation() as String == "http://localhost:8080/widgets/wid"

		when:
		getResponse.getResponseAsType(Widget)

		then:
		thrown NotFoundException
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
		getResponse.getResponseAsType(Widget)

		then:
		HttpClientException ex = thrown(HttpClientException)
		ex.getStatus() == 500
	}
}
