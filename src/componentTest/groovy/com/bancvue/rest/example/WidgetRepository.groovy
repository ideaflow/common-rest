package com.bancvue.rest.example

import org.springframework.stereotype.Component

@Component
class WidgetRepository {
	@Delegate
	Map<String, Widget> widgets = [:]

}
