package com.bancvue.rest;

public class HttpClientException extends RuntimeException {

    public static HttpClientException unexpected(int statusCode) {
        return new HttpClientException("Unexpected status code=${statusCode}", statusCode);
    }


    private int statusCode;

    public HttpClientException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

}
