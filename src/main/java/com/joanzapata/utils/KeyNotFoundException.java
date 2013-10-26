package com.joanzapata.utils;

public class KeyNotFoundException extends RuntimeException {

    public KeyNotFoundException(String key, String formattingString) {
        super("Couldn't find key \"" + key + "\" in string \"" + formattingString + "\".");
    }
}
