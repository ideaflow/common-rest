package com.bancvue.rest.jaxrs;

import javax.ws.rs.core.Response;

public class UnprocessableEntityStatusType implements Response.StatusType {
	@Override
	public int getStatusCode() {
		return 422;
	}

	@Override
	public Response.Status.Family getFamily() {
		return Response.Status.Family.CLIENT_ERROR;
	}

	@Override
	public String getReasonPhrase() {
		return "Unprocessable Entity";
	}
}
