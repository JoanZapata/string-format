package com.joanzapata.utils;

public class MissingKeyException extends RuntimeException {

    public MissingKeyException(String key) {
        super("You didn't pass an arg for key " + key);
    }
}
