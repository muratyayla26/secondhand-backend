package com.yayla.secondhand.secondhandbackend.system.utility;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NormalizeHelper {

    private final static String UNDER_SCORE = "_";
    private final static String SPACE = " ";

    public static String replaceSpaces(String input) {
        return input.replaceAll(SPACE, UNDER_SCORE);
    }
}
