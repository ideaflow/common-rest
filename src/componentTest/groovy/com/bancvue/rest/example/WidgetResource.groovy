package com.bancvue.rest.example

import com.bancvue.rest.jaxrs.UriInfoHolder
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

	/**
	 * NOTE: Jersey will set this on every request is fine in the context of a serial test but should not be taken
	 * as an example since it is extremely unsafe.  Rather than being injected into the resource, this would normally
	 * be injected into a request-scoped implementation of UriInfoHolder.  See AbstractResource in common-spring-boot.
	 */
	@Context
	UriInfo uriInfo

	private ResourceResponseFactory responseFactory

	@PostConstruct
	private initializeResponseFactory() {
		responseFactory = new ResourceResponseFactory(WidgetResource, new UriInfoHolder() {
			@Override
			UriInfo getUriInfo() {
				return WidgetResource.this.uriInfo;
			}
		});
	}

	@GET
	public List<Widget> listWidgets() {
		widgetRepository.values() as List
	}

	private void evalWidget(Widget widget) {
		if (widget?.codeToEval) {
			Eval.me(widget.codeToEval)
		}
	}

	@GET
	@Path("/{id}")
	public Response getWidget(@PathParam("id") String id) {
		Widget widget = widgetRepository.get(id)
		evalWidget(widget)
		responseFactory.createGetResponse(id, widget)
	}

	@POST
	public Response createWidget(@Valid Widget widget) {
		Widget existingWidget = widgetRepository.get(widget.id)
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
		if (!widgetRepository.get(id)) {
			return responseFactory.createNotFoundResponse(id)
		}
		widgetRepository.put(id, update)
		evalWidget(update)
		responseFactory.createPutSuccessResponse(id, update)
	}

	@DELETE
	@Path("/{id}")
	public Response deleteWidget(@PathParam("id") String id) {
		Widget deletedWidget = widgetRepository.remove(id)
		evalWidget(deletedWidget)
		if (deletedWidget && deletedWidget.deletedItemNotIncludedInResultBody) {
			return responseFactory.createDeleteSuccessResponse(id)
		}
		responseFactory.createDeleteResponse(id, deletedWidget)
	}
}
