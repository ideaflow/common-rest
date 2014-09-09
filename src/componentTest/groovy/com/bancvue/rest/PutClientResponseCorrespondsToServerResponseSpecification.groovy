package com.bancvue.rest

import com.bancvue.rest.client.response.UpdateResponse
import com.bancvue.rest.example.Widget
import com.bancvue.rest.example.WidgetResource
import com.bancvue.rest.exception.ConflictingEntityException
import com.bancvue.rest.exception.NotFoundException
import javax.ws.rs.WebApplicationException

class PutClientResponseCorrespondsToServerResponseSpecification extends BaseTestSpec {

	private Widget addWidget(String id) {
		Widget widget = new Widget(id: id)
		widgetRepository.put(id, widget)
		widget
	}

	def "success should return status code 200, client response should convert and return entity"() {
		Widget update = addWidget("updated")

		when:
		UpdateResponse updateResponse = clientRequest.path(update.id).updateWithPut(update)

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

	def "not found should return status code 404, client response should convert to NotFoundException"() {
		Widget widget = new Widget(id: "wid")

		when:
		UpdateResponse putResponse = clientRequest.path("wid").updateWithPut(widget)

		then:
		putResponse.clientResponse.getStatus() == 404

		when:
		putResponse.getValidatedResponse(Widget)

		then:
		thrown NotFoundException
	}

	def "object already exists should return status code 409 with entity, client response should convert to exception with data"() {
		Widget widget = new Widget(id: WidgetResource.CONFLICT_WITH_DATA_ID)

		when:
		UpdateResponse putResponse = clientRequest.path(WidgetResource.CONFLICT_WITH_DATA_ID).updateWithPut(widget)

		then:
		putResponse.clientResponse.getStatus() == 409

		when:
		putResponse.getValidatedResponse(Widget)

		then:
		ConflictingEntityException ex = thrown(ConflictingEntityException)
		ex.response.status == 409
		ex.entity == widget
	}

	def "application error should return status code 500, client response should convert to WebApplicationException"() {
		Widget widget = addWidget("updated")
		widget.initApplicationError()

		when:
		UpdateResponse updateResponse = clientRequest.path(widget.id).updateWithPut(widget)

		then:
		updateResponse.clientResponse.getStatus() == 500

		when:
		updateResponse.getValidatedResponse(Widget)

		then:
		WebApplicationException ex = thrown()
		ex.response.status == 500
	}

}
