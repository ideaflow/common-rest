package com.bancvue.rest.example

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.validation.constraints.NotNull


@ToString
@EqualsAndHashCode
class Widget {

	@NotNull
	@JsonProperty
	String id

	@JsonProperty
	String codeToEval

	@JsonProperty
	boolean deletedItemNotIncludedInResultBody

	void initApplicationError() {
		codeToEval = "throw new RuntimeException('failure!')"
	}

	@JsonIgnore
	boolean getDeletedItemNotIncludedInResultBody() {
		return deletedItemNotIncludedInResultBody
	}

}
