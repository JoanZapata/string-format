package com.joanzapata.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Usage:
 * <p/>
 * <pre>
 *     Strings.format("Hello {firstname} {lastname}!")
 *         .with("firstname", "John")
 *         .with("lastname", "Doe")
 *         .build();
 * </pre>
 * @return The formatted string.
 */
public final class Strings {

    private Strings() {
        // Prevents instantiation
    }

    /**
     * Usage:
     * <p/>
     * <pre>
     *     Strings.format("Hello {firstname} {lastname}!")
     *         .with("firstname", "John")
     *         .with("lastname", "Doe")
     *         .build();
     * </pre>
     * @return The formatted string.
     */
    public static Builder format(String string) {
        return new Builder(string);
    }

    public static class Builder {

        private final static Pattern pattern = Pattern.compile("\\{.*?\\}");

        private String baseString;

        private Builder(String string) {
            baseString = string;
        }

        /**
         * @param key   The key, without the '{}'.
         * @param value The value to put for that key.
         * @return The builder for DSL.
         */
        public Builder with(String key, Object value) {
            if (value == null) value = "";
            if (!baseString.contains("{" + key + "}"))
                throw new KeyNotFoundException(key, baseString);
            baseString = baseString.replace("{" + key + "}", value.toString());
            return this;
        }

        /** Create the final string. */
        public String build() {
            final Matcher matcher = pattern.matcher(baseString);
            if (matcher.find()) {
                throw new MissingKeyException(matcher.group());
            } else {
                return baseString;
            }
        }

    }
}
