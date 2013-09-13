package com.bancvue.rest.example

import com.yammer.dropwizard.Service
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Configuration
import com.yammer.dropwizard.config.Environment


class WidgetService extends Service<Config> {

	static class Config extends Configuration {
		String example
	}


	private WidgetResource widgetResource = new WidgetResource()

	@Override
	void initialize(Bootstrap<Config> bootstrap) {
	}

	@Override
	void run(Config configuration, Environment environment) throws Exception {
		environment.addResource(widgetResource)
	}

}
