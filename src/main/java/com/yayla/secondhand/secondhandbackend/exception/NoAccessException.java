package com.yayla.secondhand.secondhandbackend.exception;

public class NoAccessException extends RuntimeException {

    public NoAccessException() {
        super("You're not authorized for this action.");
    }

    public NoAccessException(String message){
        super(message);
    }
}
