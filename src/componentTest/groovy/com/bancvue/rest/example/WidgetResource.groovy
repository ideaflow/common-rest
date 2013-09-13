package com.bancvue.rest.example

import com.bancvue.rest.server.ResponseFactory
import groovy.util.logging.Slf4j

import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Slf4j
@Path("/widgets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class WidgetResource {

	Map<String, Widget> widgets = [:]
	private ResponseFactory responseFactory = new ResponseFactory(WidgetResource)

	@GET
	public List<Widget> listWidgets() {
		widgets.values() as List
	}

	private void evalWidget(Widget widget) {
		if (widget?.codeToEval) {
			Eval.me(widget.codeToEval)
		}
	}

	@GET
	@Path("/{id}")
	public Response getWidget(@PathParam("id") String id) {
		Widget widget = widgets[id]
		evalWidget(widget)
		responseFactory.createGetResponse(id, widget)
	}

	@POST
	public Response createWidget(@Valid Widget widget) {
		Widget existingWidget = widgets[widget.id]
		if (existingWidget) {
			return responseFactory.createAddFailedBecauseAlreadyExistsResponse(existingWidget.id, existingWidget)
		}
		evalWidget(widget)
		responseFactory.createAddSuccessResponse(widget.id, widget)
	}

//	@PUT
//	@Path("/{id}")
//	public Response updateWidget(@PathParam("id") String widgetId) {
//		responseFactory.createUpdateSuccessResponse()
//	}
//
//	@DELETE
//	@Path("/{id}")
//	public Response deleteWidget(@PathParam("id") String widgetId ) {
//		Widget deletedWidget = widgets[widgetId]
//		if (deletedWidget == null) {
//			throw HttpClientException.unexpected()
//		}
//
//		responseFactory.createDeleteResponse(deletedWidget)
//	}

}
