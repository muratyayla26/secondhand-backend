package com.yayla.secondhand.secondhandbackend.model.enumtype;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductType {
    MISCELLANEOUS(0),
    ELECTRONICS(1),
    FURNITURE(2),
    HOME_GARDEN(3),
    SPORTS_OUTDOORS(4),
    HOBBIES(5),
    FOR_KIDS(6),
    VEHICLE(7),
    FASHION(8);

    private final int value;

    @JsonValue
    public int getValue() {
        return value;
    }
}
