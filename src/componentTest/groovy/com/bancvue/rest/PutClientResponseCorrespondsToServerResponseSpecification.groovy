package com.bancvue.rest
import com.bancvue.rest.client.ClientRequestExecutor
import com.bancvue.rest.client.response.UpdateResponse
import com.bancvue.rest.example.Widget
import com.bancvue.rest.example.WidgetResource
import com.bancvue.rest.exception.ConflictException
import com.bancvue.rest.exception.ConflictingEntityException
import com.bancvue.rest.exception.HttpClientException
import spock.lang.Shared

import javax.ws.rs.client.WebTarget

class PutClientResponseCorrespondsToServerResponseSpecification extends BaseTestSpec {

	@Shared
	private WebTarget widgetResource
	private ClientRequestExecutor clientRequestExecutor

	void setup() {
		widgetResource = baseServiceResource.path("widgets")
		clientRequestExecutor = new ClientRequestExecutor()
		widgetRepository.clear()
	}

	private Widget addWidget(String id) {
		Widget widget = new Widget(id: id)
		widgetRepository.put(id, widget)
		widget
	}

	def "success should return status code 200, client response should convert and return entity"() {
		Widget update = addWidget("updated")

		when:
		UpdateResponse updateResponse = clientRequestExecutor.updateWithPut(widgetResource.path(update.id), update)

		then:
		updateResponse.clientResponse.getStatus() == 200

		when:
		Widget actualResponse = updateResponse.getValidatedResponse(Widget)

		then:
		update == actualResponse
		!update.is(actualResponse)
	}

	// TODO: what behavior do we want around PUT?
	// do we allow create?

	def "not found should return status code 404, client response should convert to http exception"() {
		Widget widget = new Widget(id: "wid")

		when:
		UpdateResponse putResponse = clientRequestExecutor.updateWithPut(widgetResource.path("wid"), widget)

		then:
		putResponse.clientResponse.getStatus() == 404

		when:
		putResponse.getValidatedResponse(Widget)

		then:
		HttpClientException ex = thrown(HttpClientException)
		ex.status == 404
	}
	
	
	def "object already exists should return status code 409 with entity, client response should convert to exception with data"() {
		Widget widget = new Widget(id: WidgetResource.CONFLICT_WITH_DATA_ID)

		when:
		UpdateResponse putResponse = clientRequestExecutor.updateWithPut(widgetResource.path(WidgetResource.CONFLICT_WITH_DATA_ID), widget)

		then:
		putResponse.clientResponse.getStatus() == 409

		when:
		putResponse.getValidatedResponse(Widget)

		then:
		ConflictingEntityException ex = thrown(ConflictingEntityException)
		ex.getStatus() == 409
		ex.entity == widget
	}	
	
	@Deprecated
	def "object already exists should return status code 409 with entity, client response should convert to exception with no data"() {
		Widget widget = new Widget(id: WidgetResource.CONFLICT_WITH_NO_DATA_ID_DEPRECATED)

		when:
		UpdateResponse putResponse = clientRequestExecutor.updateWithPut(widgetResource.path(WidgetResource.CONFLICT_WITH_NO_DATA_ID_DEPRECATED), widget)

		then:
		putResponse.clientResponse.getStatus() == 409

		when:
		putResponse.getValidatedResponse(Widget)

		then:
		ConflictException ex = thrown(ConflictException)
		ex.getStatus() == 409
	}



	def "application error should return status code 500, client response should convert to http exception"() {
		Widget widget = addWidget("updated")
		widget.initApplicationError()

		when:
		UpdateResponse updateResponse = clientRequestExecutor.updateWithPut(widgetResource.path(widget.id), widget)

		then:
		updateResponse.clientResponse.getStatus() == 500

		when:
		updateResponse.getValidatedResponse(Widget)

		then:
		HttpClientException ex = thrown(HttpClientException)
		ex.status == 500
	}

}
