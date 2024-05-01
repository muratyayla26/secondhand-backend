package com.yayla.secondhand.secondhandbackend.model.enumtype;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderType {
    MALE(0),
    FEMALE(1),
    NON_BINARY(2);

    private final int value;

    @JsonValue
    public int getValue() {
        return value;
    }
}
