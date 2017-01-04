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
