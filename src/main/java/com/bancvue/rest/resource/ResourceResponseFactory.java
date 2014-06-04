package com.bancvue.rest.resource;

import com.bancvue.rest.Envelope;

import com.bancvue.rest.jaxrs.UriInfoHolder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

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

	private URI getTargetResourceLocation(String pathToEntity) {
		return getUriInfo().getBaseUriBuilder()
				.path(targetResource)
				.path(pathToEntity)
				.build();
	}

	public Response createNotFoundResponse(String pathToEntity) {
		return Response.status(Response.Status.NOT_FOUND)
				.location(getTargetResourceLocation(pathToEntity))
				.build();
	}

    public Response createConflictResponse(String pathToEntity) {
        return Response.status(Response.Status.CONFLICT)
                .location(getTargetResourceLocation(pathToEntity))
                .build();
    }

	public Response createSeeOtherResponse(String pathToEntity) {
		URI uri = getTargetResourceLocation(pathToEntity);
		return Response.seeOther(uri)
				.location(uri)
				.build();
	}

	public <T> Response createGetResponse(String pathToEntity, T entity) {
		if (entity != null) {
			return createGetSuccessResponse(pathToEntity, entity);
		} else {
			return createNotFoundResponse(pathToEntity);
		}
	}

	public <T> Response createGetResponse(String pathToEntity, Envelope<T> envelope) {
		if (envelope.getData() != null) {
			return createGetSuccessResponse(pathToEntity, envelope);
		} else {
			return createNotFoundResponse(pathToEntity);
		}
	}

	public Response createGetSuccessResponse(String pathToEntity, Object entity) {
		return Response.ok()
				.type(MediaType.APPLICATION_JSON_TYPE)
				.location(getTargetResourceLocation(pathToEntity))
				.entity(entity)
				.build();
	}

	public Response createPostSuccessResponse(String pathToEntity, Object entity) {
		return Response.created(getTargetResourceLocation(pathToEntity))
				.type(MediaType.APPLICATION_JSON_TYPE)
				.entity(entity)
				.build();
	}

	public Response createPostFailedBecauseAlreadyExistsResponse(String pathToEntity, Object existingEntity) {
		return Response.status(Response.Status.CONFLICT)
				.location(getTargetResourceLocation(pathToEntity))
				.type(MediaType.APPLICATION_JSON_TYPE)
				.entity(existingEntity)
				.build();
	}

	public Response createPutSuccessResponse(String pathToEntity, Object updatedEntity) {
		return Response.ok()
				.type(MediaType.APPLICATION_JSON_TYPE)
				.location(getTargetResourceLocation(pathToEntity))
				.entity(updatedEntity)
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
