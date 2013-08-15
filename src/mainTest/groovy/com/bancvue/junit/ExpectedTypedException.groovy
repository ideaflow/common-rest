package com.bancvue.junit

import org.hamcrest.Matcher
import org.junit.rules.ExpectedException
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

abstract class ExpectedTypedException implements TestRule {

    class Builder<T extends Builder> {

        T withMatcher(Matcher<?> matcher) {
            delegate.expect(matcher);
            this
        }

        T withCause(Matcher<? extends Throwable> expectedCause) {
            delegate.expectCause(expectedCause);
        }

        T withMessage(String substring) {
            delegate.expectMessage(substring);
            this
        }

        T withMessage(Matcher<String> matcher) {
            delegate.expectMessage(matcher);
            this
        }

    }


    protected ExpectedException delegate = ExpectedException.none();

    public Statement apply(Statement base, Description description) {
        return delegate.apply(base, description);
    }

}
