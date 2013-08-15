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

//    Response createAddSuccessResponse(String pathToEntity) {
//        // TODO: the commented out code comes straight out of the dropwizard example; however, in practice the
//        // UriBuilder.fromResource(targetResource) is getting double-prepended when returned to the client, so
//        // it looks like the path needs to be relative
////        URI location = UriBuilder.fromResource(targetResource)
////                .path(pathToEntity)
////                .build()
//
//        URI location = UriBuilder.fromPath(pathToEntity).build()
//        Response.created(location).build()
//    }

    public Response createAddSuccessResponse(String pathToEntity, Object entity) {
//        // TODO: the commented out code comes straight out of the dropwizard example; however, in practice the
//        // UriBuilder.fromResource(targetResource) is getting double-prepended when returned to the client, so
//        // it looks like the path needs to be relative
////        URI location = UriBuilder.fromResource(targetResource)
////                .path(pathToEntity)
////                .build()

        URI location = UriBuilder.fromPath(pathToEntity).build();
        return Response.created(location)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(entity)
                .build();
    }

    public Response createUpdateSuccessResponse(String pathToEntity, Object entity) {
        URI location = UriBuilder.fromPath(pathToEntity).build();
        return Response.created(location)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(entity)
                .build();
    }

    public Response createAddFailedBecauseAlreadyExistsResponse(Object existingEntity) {
        // TODO: for some reason, jersey always converts response code 409 to text/html so I'm not sure how to return the existing entity
        return Response.status(Response.Status.CONFLICT)
//                .type(MediaType.APPLICATION_JSON_TYPE)
//                .entity(existingEntity)
                .build();
    }

    public Response createDeleteResponse(Object deletedEntity) {
        Response response;
        if (deletedEntity != null) {
            response = Response.ok(deletedEntity)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
        } else {
            response = Response.noContent().build();
        }
        return response;
    }

}
