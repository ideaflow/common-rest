/**
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
package com.bancvue.rest.params;

/**
 * Borrowed from DropWizard
 *
 * https://github.com/dropwizard/dropwizard/blob/master/LICENSE
 **/

/**
 * A parameter encapsulating long values. All non-decimal values will return a
 * {@code 400 Bad Request} response.
 */
public class LongParam extends AbstractParam<Long> {
	public LongParam(String input) {
		super(input);
	}

	@Override
	protected String errorMessage(String input, Exception e) {
		return '"' + input + "\" is not a number.";
	}

	@Override
	protected Long parse(String input) {
		return Long.valueOf(input);
	}
}
