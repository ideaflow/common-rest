package com.bancvue.rest

import com.bancvue.rest.client.ClientResponseFactory
import com.bancvue.rest.client.CreateResponse
import com.bancvue.rest.example.Widget
import com.bancvue.rest.example.WidgetServiceRule
import com.bancvue.rest.exception.HttpClientException
import com.sun.jersey.api.client.WebResource
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

class CreateSpecification extends Specification {

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

	def "success should return status code 201 and location, client response should convert and return entity"() {
		Widget widget = new Widget(id: "created")

		when:
		CreateResponse createResponse = clientResponseFactory.create(widgetResource, widget)

		then:
		assert createResponse.response.getStatus() == 201
		assert createResponse.response.getLocation() as String == "http://localhost:8080/widgets/created"

		when:
		Widget actualWidget = createResponse.assertEntityCreatedAndGet(Widget)

		then:
		assert widget == actualWidget
		assert !widget.is(actualWidget)
	}

	def "object already exists should return status code 409 and location, client response should convert to exception"() {
		Widget widget = addWidget("duplicate")

		when:
		CreateResponse createResponse = clientResponseFactory.create(widgetResource, widget)

		then:
		assert createResponse.response.getStatus() == 409
		assert createResponse.response.getLocation() as String == "http://localhost:8080/widgets/duplicate"

		when:
		createResponse.assertEntityCreatedAndGet(Widget)

		then:
		HttpClientException ex = thrown(HttpClientException)
		assert ex.getStatus() == 409

		// TODO: how to return the existing entity in the body of the result?
	}

	def "invalid object should return status code 422, client response should convert to exception"() {
		Widget invalid = new Widget()

		when:
		CreateResponse createResponse = clientResponseFactory.create(widgetResource, invalid)

		then:
		assert createResponse.response.getStatus() == 422
		assert createResponse.response.getLocation() == null

		when:
		createResponse.assertEntityCreatedAndGet(Widget)

		then:
		HttpClientException ex = thrown(HttpClientException)
		assert ex.getStatus() == 422

		// TODO: what about the body?  can we standardize on reporting invalid objects?
	}

	def "application error should return status code 500, client response should convert to http exception"() {
		Widget widget = new Widget(id: "app-error")
		widget.initApplicationError()

		when:
		CreateResponse createResponse = clientResponseFactory.create(widgetResource, widget)

		then:
		assert createResponse.response.getStatus() == 500
		assert createResponse.response.getLocation() == null

		when:
		createResponse.assertEntityCreatedAndGet(Widget)

		then:
		HttpClientException ex = thrown(HttpClientException)
		assert ex.getStatus() == 500
	}
}
