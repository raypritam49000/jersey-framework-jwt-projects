package com.jersey.rest.api.exception;

public class BadCredentialsException extends  RuntimeException{
    public BadCredentialsException() {
        super();
    }

    public BadCredentialsException(String message) {
        super(message);
    }
}
