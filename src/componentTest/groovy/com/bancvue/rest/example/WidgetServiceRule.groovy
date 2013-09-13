package com.bancvue.rest.example

import com.google.common.io.Resources
import com.sun.jersey.api.client.Client
import com.sun.jersey.api.client.WebResource
import com.yammer.dropwizard.client.JerseyClientBuilder
import com.yammer.dropwizard.client.JerseyClientConfiguration
import com.yammer.dropwizard.testing.junit.DropwizardServiceRule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class WidgetServiceRule implements TestRule {


    static WidgetServiceRule create() {
        create("widget-config.yml")
    }

    static WidgetServiceRule create(String configFileName) {
        String configFilePath = Resources.getResource(configFileName).path
	    DropwizardServiceRule dropwizardServiceRule =
		    new DropwizardServiceRule<WidgetService.Config>(WidgetService.class, configFilePath)
	    new WidgetServiceRule(dropwizardServiceRule)
    }



    private Client jerseyClient
    private WebResource baseServiceResource
	private DropwizardServiceRule dropwizardRule

	WidgetServiceRule(DropwizardServiceRule dropwizardRule) {
		this.dropwizardRule = dropwizardRule
    }

	@Override
	Statement apply(Statement base, Description description) {
		Statement statement = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				try {
					jerseyClient = createJerseyClient(dropwizardRule)
					baseServiceResource = createLocalServiceResource(dropwizardRule, jerseyClient)
					base.evaluate()
				} finally {
					jerseyClient.destroy()
					jerseyClient = null
					baseServiceResource = null
				}
			}
		};
		dropwizardRule.apply(statement, description)
	}

	WebResource getBaseServiceResource() {
		return baseServiceResource
	}

	WidgetService getService() {
		(WidgetService) dropwizardRule.service
	}

	Map<String, Widget> getWidgets() {
		return service.widgetResource.widgets
	}

	private Client createJerseyClient(DropwizardServiceRule serviceRule) {
        new JerseyClientBuilder().using(new JerseyClientConfiguration())
                    .using(serviceRule.environment)
                    .build();
    }

    private WebResource createLocalServiceResource(DropwizardServiceRule serviceRule, Client client) {
        String url = "http://localhost:${serviceRule.configuration.httpConfiguration.port}"
	    client.resource(url)
    }

}