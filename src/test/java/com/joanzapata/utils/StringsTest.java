package com.joanzapata.utils;

import org.junit.Assert;
import org.junit.Test;

public class StringsTest {

    @Test
    public void format_nominal() {
        Assert.assertEquals("Hello John Doe!",
                Strings.format("Hello {firstname} {lastname}!")
                        .with("firstname", "John")
                        .with("lastname", "Doe")
                        .build());
    }

    @Test(expected = MissingKeyException.class)
    public void format_missingArg() {
        Assert.assertEquals("Hello John Doe!",
                Strings.format("Hello {firstname} {lastname}!")
                        .with("firstname", "John")
                        .build());
    }

    @Test(expected = KeyNotFoundException.class)
    public void format_extraArg() {
        Assert.assertEquals("Hello John Doe!",
                Strings.format("Hello {firstname} {lastname}!")
                        .with("firstname", "John")
                        .with("lastname", "John")
                        .with("extra", "Extra")
                        .build());
    }
}
