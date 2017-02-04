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

import org.glassfish.jersey.server.ResourceConfig;

import java.util.HashSet;
import java.util.Set;

public class ExceptionMapperConfig extends ResourceConfig {

	public static Set<Class<?>> getExceptionMapperConfigs() {
		HashSet<Class<?>> configs = new HashSet<Class<?>>();
		configs.add(GenericExceptionMapper.class);
		configs.add(InvalidEntityExceptionMapper.class);
		configs.add(InvalidFormatExceptionMapper.class);
		configs.add(JsonMappingExceptionMapper.class);
		configs.add(JsonParseExceptionMapper.class);
		configs.add(ValidationExceptionMapper.class);
		configs.add(WebApplicationExceptionMapper.class);
		return configs;
	}

}
