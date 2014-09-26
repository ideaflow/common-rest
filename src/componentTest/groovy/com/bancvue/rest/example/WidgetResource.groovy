/*
 * Copyright 2014 BancVue, LTD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bancvue.rest.example

import com.bancvue.rest.jaxrs.UriInfoHolder
import com.bancvue.rest.resource.ResourceResponseFactory
import groovy.util.logging.Slf4j
import javax.annotation.PostConstruct
import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo
import org.springframework.beans.factory.annotation.Autowired

@Slf4j
@Path("/widgets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class WidgetResource {

	static String CONFLICT_WITH_DATA_ID = "conflictWithDataId";

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
		responseFactory.createGetResponse(widget)
	}

	@POST
	public Response createWidget(@Valid Widget widget) {
		Widget existingWidget = widgetRepository.get(widget.id)
		evalWidget(existingWidget)
		if (existingWidget) {
			return responseFactory.createPostFailedBecauseAlreadyExistsResponse(existingWidget)
		}
		evalWidget(widget)
		responseFactory.createPostSuccessResponse(widget.id, widget)
	}

	@PUT
	@Path("/{id}")
	public Response updateWidget(@PathParam("id") String id, @Valid Widget update) {
		if (CONFLICT_WITH_DATA_ID.equals(id)) {
			return responseFactory.createConflictResponse(update);
		}
		if (!widgetRepository.get(id)) {
			return responseFactory.createNotFoundResponse()
		}
		widgetRepository.put(id, update)
		evalWidget(update)
		responseFactory.createPutSuccessResponse(update)
	}

	@DELETE
	@Path("/{id}")
	public Response deleteWidget(@PathParam("id") String id) {
		Widget deletedWidget = widgetRepository.remove(id)
		evalWidget(deletedWidget)
		responseFactory.createDeleteResponse(deletedWidget)
	}
}
