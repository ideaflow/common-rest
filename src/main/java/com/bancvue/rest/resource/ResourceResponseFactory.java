package com.bancvue.rest.resource;

import com.bancvue.rest.Envelope;
import com.bancvue.rest.jaxrs.UriInfoHolder;
import java.net.URI;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

public class ResourceResponseFactory {

	private Class targetResource;
	private UriInfoHolder uriInfoHolder;

	public ResourceResponseFactory(Class targetResource, UriInfoHolder uriInfoHolder) {
		this.targetResource = targetResource;
		this.uriInfoHolder = uriInfoHolder;
	}

	private UriInfo getUriInfo() {
		return uriInfoHolder.getUriInfo();
	}

	public URI getTargetResourceLocation(String pathToEntity) {
		return getTargetResourceBuilder()
				.path(pathToEntity)
				.build();
	}

	public URI getTargetResourceLocation(String pathToEntity, Map<String, Object> templateValues) {
		return getTargetResourceBuilder()
				.path(pathToEntity)
				.resolveTemplates(templateValues)
				.build();
	}

	public UriBuilder getTargetResourceBuilder() {
		return getUriInfo().getBaseUriBuilder()
				.path(targetResource);
	}

	@Deprecated
	public Response createNotFoundResponse(String pathToEntity) {
		return createNotFoundResponse();
	}

	public Response createNotFoundResponse() {
		return Response.status(Response.Status.NOT_FOUND)
				.build();
	}
	
	public Response createMethodNotAllowedResponse() {
		return Response.status(Response.Status.METHOD_NOT_ALLOWED)
				.build();
	}

	@Deprecated
	public Response createConflictResponse(String pathToEntity) {
		return createConflictResponse();
	}

	@Deprecated
	public Response createConflictResponse() {
		return Response.status(Response.Status.CONFLICT)
				.build();
	}

	public Response createConflictResponse(Object entity) {
		return Response.status(Response.Status.CONFLICT)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.entity(entity)
				.build();
	}

	public Response createSeeOtherResponse(String pathToEntity) {
		URI uri = getTargetResourceLocation(pathToEntity);
		return createSeeOtherResponse(uri);
	}

	public Response createSeeOtherResponse(URI uri) {
		return Response.seeOther(uri)
				.location(uri)
				.build();
	}

	public Response createGetManyResponse(Object entities) {
		return Response.ok()
				.type(MediaType.APPLICATION_JSON_TYPE)
				.entity(entities)
				.build();
	}

	@Deprecated
	public Response createGetResponse(String pathToEntity, Object entity) {
		return createGetResponse(entity);
	}

	public Response createGetResponse(Object entity) {
		if (isEntityNotNull(entity)) {
			return createGetSuccessResponse(entity);
		} else {
			return createNotFoundResponse();
		}
	}

	private boolean isEntityNotNull(Object entity) {
		if (entity instanceof Envelope) {
			return ((Envelope) entity).getData() != null;
		} else {
			return entity != null;
		}
	}

	private Response createGetSuccessResponse(Object entity) {
		return Response.ok()
				.type(MediaType.APPLICATION_JSON_TYPE)
				.entity(entity)
				.build();
	}

	public Response createPostSuccessResponse(String pathToEntity, Object entity) {
		URI location = getTargetResourceLocation(pathToEntity);
		return createPostSuccessResponse(location, entity);
	}

	public Response createPostSuccessResponse(URI location, Object entity) {
		return Response.created(location)
				.location(location)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.entity(entity)
				.build();
	}

	@Deprecated
	public Response createPostFailedBecauseAlreadyExistsResponse(String pathToEntity, Object existingEntity) {
		return createPostFailedBecauseAlreadyExistsResponse(existingEntity);
	}

	public Response createPostFailedBecauseAlreadyExistsResponse(Object existingEntity) {
		return createConflictResponse(existingEntity);
	}

	@Deprecated
	public Response createPutSuccessResponse(String pathToEntity, Object updatedEntity) {
		return createPutSuccessResponse(updatedEntity);
	}

	public Response createPutSuccessResponse(Object updatedEntity) {
		return Response.ok()
				.type(MediaType.APPLICATION_JSON_TYPE)
				.entity(updatedEntity)
				.build();
	}

	@Deprecated
	public Response createDeleteResponse(String pathToEntity, Object deletedEntity) {
		return createDeleteResponse(deletedEntity);
	}

	public Response createDeleteResponse(Object deletedEntity) {
		if (isEntityNotNull(deletedEntity)) {
			return createDeleteSuccessResponse();
		} else {
			return createDeleteFailedBecauseObjectNotFoundResponse();
		}
	}

	private Response createDeleteSuccessResponse() {
		return Response.noContent()
				.build();
	}

	private Response createDeleteFailedBecauseObjectNotFoundResponse() {
		return Response.status(Response.Status.NOT_FOUND)
				.build();
	}

}
