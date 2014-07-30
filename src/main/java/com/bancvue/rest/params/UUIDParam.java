package com.bancvue.rest.params;

/**
 * Borrowed from DropWizard
 *
 * https://github.com/dropwizard/dropwizard/blob/master/LICENSE
**/

import java.util.UUID;

/**
 * A parameter encapsulating UUID values. All non-UUID values will return a
 * {@code 400 Bad Request} response.
 */
public class UUIDParam extends AbstractParam<UUID> {
	public UUIDParam(String input) {
		super(input);
	}

	@Override
	protected String errorMessage(String input, Exception e) {
		return '"' + input + "\" is not a UUID.";
	}

	@Override
	protected UUID parse(String input) {
		return UUID.fromString(input);
	}
}
