package com.bancvue.rest

import com.bancvue.junit.ExpectedTypedException

import javax.ws.rs.core.Response.StatusType

class ExpectedHttpClientException extends ExpectedTypedException {

    class Builder extends ExpectedTypedException.Builder<Builder> {

        Builder withStatusCode(StatusType statusType) {
            withStatusCode(statusType.statusCode)
            this
        }

        Builder withStatusCode(int statusCode) {
            delegate.expect(HttpClientExceptionStatusMatcher.hasStatusCode(statusCode))
            this
        }

    }

    static ExpectedHttpClientException none() {
        return new ExpectedHttpClientException()
    }

    private ExpectedHttpClientException() {}

    Builder expect() {
        delegate.expect(HttpClientException)
        new Builder()
    }

}