package com.bancvue.rest

import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class HttpClientExceptionStatusMatcher<T extends HttpClientException> extends
		TypeSafeMatcher<T> {

	private final Matcher<Integer> fMatcher;

	public HttpClientExceptionStatusMatcher(Matcher<Integer> matcher) {
		fMatcher = matcher;
	}

	public void describeTo(Description description) {
		description.appendText("exception with statusCode ");
		description.appendDescriptionOf(fMatcher);
	}

	@Override
	protected boolean matchesSafely(T item) {
		return fMatcher.matches(item.getStatus())
	}

	@Override
	protected void describeMismatchSafely(T item, Description description) {
		description.appendText("message ");
		fMatcher.describeMismatch(item.getStatus(), description);
	}

	public static <T extends HttpClientException> Matcher<T> hasStatusCode(int statusCode) {
		Matcher<Integer> matcher = CoreMatchers.equalTo(statusCode)
		hasStatusCode(matcher)
	}

	@org.hamcrest.Factory
	public static <T extends HttpClientException> Matcher<T> hasStatusCode(final Matcher<Integer> matcher) {
		return new HttpClientExceptionStatusMatcher<T>(matcher);
	}

}
