package com.bancvue.rest.example

import org.glassfish.jersey.server.ServerProperties
import org.glassfish.jersey.servlet.ServletContainer
import org.glassfish.jersey.servlet.ServletProperties
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.embedded.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan

@EnableAutoConfiguration
@ComponentScan(basePackages = ["com.bancvue.rest"])
class WidgetService {
	@Bean
	public ServletRegistrationBean jerseyServlet() {
		ServletContainer container = new ServletContainer();
		ServletRegistrationBean registration = new ServletRegistrationBean(container, "/*");

		registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());
		registration.addInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
		registration.addInitParameter(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, "true");

		return registration;
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(WidgetService.class).run(args);
	}

}
