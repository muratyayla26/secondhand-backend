package com.yayla.secondhand.secondhandbackend.exception;


public class TokenRefreshException extends RuntimeException {

    public TokenRefreshException() {
    }

    public TokenRefreshException(String message) {
        super(message);
    }
}