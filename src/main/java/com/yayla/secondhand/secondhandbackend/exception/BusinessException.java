package com.yayla.secondhand.secondhandbackend.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    public BusinessException() {
        super("An unexpected error occurred.");
    }

    public BusinessException(String message) {
        super(message);
    }
}
