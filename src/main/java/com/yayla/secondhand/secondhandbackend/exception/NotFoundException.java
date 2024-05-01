package com.yayla.secondhand.secondhandbackend.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("Data not found.");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
