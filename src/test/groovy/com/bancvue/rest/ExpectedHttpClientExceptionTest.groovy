package com.bancvue.rest

import org.junit.Ignore
import org.junit.Rule
import org.junit.Test


class ExpectedHttpClientExceptionTest {

	@Rule
	public ExpectedHttpClientException exception = ExpectedHttpClientException.none()

	@Test
	void shouldPass_IfNoExpectationIsGiven() {
	}

	@Test
	void expect_ShouldPassIfHttpClientExceptionIsThrown() {
		exception.expect()

		throw HttpClientException.unexpected(404)
	}

	@Test
	void expectWithStatusCode_ShouldPass_IfStatusCodeMatches() {
		exception.expect().withStatusCode(409)

		throw HttpClientException.unexpected(409)
	}

	@Test
	void expectWithMessage_ShouldPass_IfMessageMatches() {
		exception.expect().withMessage("some message")

		throw new HttpClientException("some message", 500)
	}

	// not sure how to verify this behavior since the failure is thrown from within junit internals
	@Ignore
	@Test
	void expectWithStatusCode_ShouldFail_IfStatusCodeDoesNotMatch() {
		exception.expect().withStatusCode(405)

		throw HttpClientException.unexpected(410)
	}

}
