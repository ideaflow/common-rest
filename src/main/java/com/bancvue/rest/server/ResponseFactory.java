package com.bancvue.rest.server;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class ResponseFactory {

	private Class targetResource;

	public ResponseFactory(Class targetResource) {
		this.targetResource = targetResource;
	}

	private URI getTargetResourceLocation(String pathToEntity) {
		return UriBuilder.fromResource(targetResource)
				.path(pathToEntity)
				.build();
	}

	public Response createGetResponse(String pathToEntity, Object entity) {
		if (entity != null) {
			return createGetSuccessResponse(pathToEntity, entity);
		} else {
			return createGetFailedResponse(pathToEntity);
		}
	}

	public Response createGetFailedResponse(String pathToEntity) {
		return Response.status(Response.Status.NOT_FOUND)
				.location(getTargetResourceLocation(pathToEntity))
				.build();
	}

	public Response createGetSuccessResponse(String pathToEntity, Object entity) {
		return Response.ok()
				.type(MediaType.APPLICATION_JSON_TYPE)
				.location(getTargetResourceLocation(pathToEntity))
				.entity(entity)
				.build();
	}

	public Response createAddSuccessResponse(String pathToEntity, Object entity) {
		// TODO: we should be able to do use getTargetResourceLocation and do something like this...
		// URI location = getTargetResourceLocation(pathToEntity)
		// however, jersey seems to double-prepend the resource base when it returns the result to the client;
		// oddly enough, this only seems to happen with 201 (CREATED) responses
		URI location = UriBuilder.fromPath(pathToEntity).build();
		return Response.created(location)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.entity(entity)
				.build();
	}

	public Response createAddFailedBecauseAlreadyExistsResponse(String pathToEntity, Object existingEntity) {
		// TODO: for some reason, jersey always converts response code 409 to text/html so I'm not sure how to return the existing entity
		return Response.status(Response.Status.CONFLICT)
				.location(getTargetResourceLocation(pathToEntity))
//              .type(MediaType.APPLICATION_JSON_TYPE)
//              .entity(existingEntity)
				.build();
	}

	public Response createUpdateSuccessResponse(String pathToEntity) {
		return Response.noContent()
				.type(MediaType.APPLICATION_JSON_TYPE)
				.location(getTargetResourceLocation(pathToEntity))
				.build();
	}

	public Response createDeleteResponse(String pathToEntity, Object deletedEntity) {
		if (deletedEntity != null) {
			return createDeleteSuccessResponse(pathToEntity, deletedEntity);
		} else {
			return createDeleteFailedBecauseObjectNotFoundResponse(pathToEntity);
		}
	}

	public Response createDeleteSuccessResponse(String pathToEntity) {
		return Response.noContent()
				.location(getTargetResourceLocation(pathToEntity))
				.build();
	}

	public Response createDeleteSuccessResponse(String pathToEntity, Object deletedEntity) {
		return Response.ok(deletedEntity)
				.location(getTargetResourceLocation(pathToEntity))
				.type(MediaType.APPLICATION_JSON_TYPE)
				.build();
	}

	public Response createDeleteFailedBecauseObjectNotFoundResponse(String pathToEntity) {
		return Response.status(Response.Status.NOT_FOUND)
				.location(getTargetResourceLocation(pathToEntity))
				.build();
	}

}
