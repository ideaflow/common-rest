package com.bancvue.rest.example

import com.bancvue.rest.resource.ResourceResponseFactory
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

@Slf4j
@Path("/widgets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class WidgetResource {

	@Autowired
	WidgetRepository widgetRepository

	@Context
	UriInfo uriInfo

	private ResourceResponseFactory responseFactory

	@PostConstruct
	private initializeResponseFactory() {
		responseFactory = new ResourceResponseFactory(WidgetResource, uriInfo)
	}

	@GET
	public List<Widget> listWidgets() {
		widgetRepository.widgets.values() as List
	}

	private void evalWidget(Widget widget) {
		if (widget?.codeToEval) {
			Eval.me(widget.codeToEval)
		}
	}

	@GET
	@Path("/{id}")
	public Response getWidget(@PathParam("id") String id) {
		Widget widget = widgetRepository.widgets[id]
		evalWidget(widget)
		responseFactory.createGetResponse(id, widget)
	}

	@POST
	public Response createWidget(@Valid Widget widget) {
		Widget existingWidget = widgetRepository.widgets[widget.id]
		evalWidget(existingWidget)
		if (existingWidget) {
			return responseFactory.createPostFailedBecauseAlreadyExistsResponse(existingWidget.id, existingWidget)
		}
		evalWidget(widget)
		responseFactory.createPostSuccessResponse(widget.id, widget)
	}

	@PUT
	@Path("/{id}")
	public Response updateWidget(@PathParam("id") String id, @Valid Widget update) {
		if (!widgetRepository.widgets[id]) {
			return responseFactory.createNotFoundResponse(id)
		}
		widgetRepository.widgets[id] = update
		evalWidget(update)
		responseFactory.createPutSuccessResponse(id, update)
	}

	@DELETE
	@Path("/{id}")
	public Response deleteWidget(@PathParam("id") String id) {
		Widget deletedWidget = widgetRepository.widgets.remove(id)
		evalWidget(deletedWidget)
		if (deletedWidget && deletedWidget.deletedItemNotIncludedInResultBody) {
			return responseFactory.createDeleteSuccessResponse(id)
		}
		responseFactory.createDeleteResponse(id, deletedWidget)
	}
}
