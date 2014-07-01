package com.bancvue.rest.params;

import java.math.BigInteger;

public class BigIntegerParam extends AbstractParam<BigInteger> {
	public BigIntegerParam(String input) {
		super(input);
	}

	@Override
	protected String errorMessage(String input, Exception e) {
		return '"' + input + "\" is not a number.";
	}	
	
	@Override
	protected BigInteger parse(String input) throws Exception {
		return new BigInteger(input);
	}
}
