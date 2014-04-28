package com.bancvue.rest.example

import com.bancvue.rest.exception.mapper.InvalidEntityExceptionMapper
import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.server.ServerProperties

import javax.ws.rs.ApplicationPath

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