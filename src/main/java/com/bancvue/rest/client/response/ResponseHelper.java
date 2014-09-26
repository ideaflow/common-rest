/**
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
package com.bancvue.rest.client.response;

import com.bancvue.rest.envelope.Envelope;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public class ResponseHelper {

	public static <T> boolean isEntityNotNull(T entity) {
		if (entity instanceof Envelope) {
			return ((Envelope) entity).getData() != null;
		}
		return entity != null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T readEntity(Response clientResponse, Object responseType) {
		if (responseType instanceof Class) {
			return clientResponse.readEntity((Class<T>) responseType);
		} else if (responseType instanceof GenericType) {
			return clientResponse.readEntity((GenericType<T>) responseType);
		} else {
			throw new IllegalArgumentException("Response type must be either Class or GenericType, was=" + responseType);
		}
	}

}
