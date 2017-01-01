/**
 * Copyright 2017 BancVue, LTD
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
package com.bancvue.rest.exception.mapper;

import com.bancvue.rest.exception.ErrorResponseFactory;
import com.fasterxml.jackson.core.JsonParseException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/*
 * Priority is set so that our version of JsonParseExceptionMapper is used instead of
 * the default com.fasterxml.jackson.jaxrs.base.JsonParseExceptionMapper.
 */
@Priority(Priorities.USER)
@Provider
public class JsonParseExceptionMapper implements ExceptionMapper<JsonParseException> {

    @Override
    public Response toResponse(JsonParseException exception) {
        return ErrorResponseFactory.makeErrorResponse(Response.Status.BAD_REQUEST, exception);
    }

}
