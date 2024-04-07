package com.yayla.secondhand.secondhandbackend.exception;


public class AuthGeneralException extends RuntimeException {

    public AuthGeneralException() {
    }

    public AuthGeneralException(String message) {
        super(message);
    }
}