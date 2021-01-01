package com.github.shoothzj.demo.agent.test.ex;

public class RestException extends Exception {

    private final int statusCode;

    public RestException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
