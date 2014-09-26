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

import com.bancvue.rest.exception.mapper.InvalidEntityExceptionMapper
import javax.ws.rs.ApplicationPath
import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.server.ServerProperties

@ApplicationPath("/")
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(WidgetResource.class);
		property(ServerProperties.JSON_PROCESSING_FEATURE_DISABLE, false);
		property(ServerProperties.MOXY_JSON_FEATURE_DISABLE, true);
		property(ServerProperties.WADL_FEATURE_DISABLE, true);
		register(InvalidEntityExceptionMapper.class);
	}
}