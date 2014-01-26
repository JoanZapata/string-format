/**
 * Copyright 2013 Joan Zapata
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.joanzapata.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.quote;

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

    /**
     * Usage:
     * <p/>
     * <pre>
     *     Strings.format("Hello [firstname] [lastname]!", "[", "]")
     *         .with("firstname", "John")
     *         .with("lastname", "Doe")
     *         .build();
     * </pre>
     * @return The formatted string.
     */
    public static Builder format(String string, String prefix, String suffix) {
        return new Builder(string, prefix, suffix);
    }

    public static class Builder {

        private final Pattern pattern;

        private String baseString;
        private String prefix;
        private String suffix;
        private boolean strictMode = true;

        private Builder(String string) {
            this(string, "{", "}");
        }

        private Builder(String string, String prefix, String suffix) {
            baseString = string;
            this.prefix = prefix;
            this.suffix = suffix;
            pattern = Pattern.compile(quote(prefix) + ".*?" + quote(suffix));
        }

        /**
         * If you set the strict mode to false, the builder won't throw any exception
         * if a key is not found or if a key is still present in the final string.
         * @param active
         * @return
         */
        public Builder strictMode(boolean active) {
            this.strictMode = active;
            return this;
        }

        /**
         * @param key   The key, without the '{}'.
         * @param value The value to put for that key.
         * @return The builder for DSL.
         */
        public Builder with(String key, Object value) {
            if (value == null) value = "";
            if (strictMode && !baseString.contains(prefix + key + suffix))
                throw new KeyNotFoundException(key, baseString);
            baseString = baseString.replace(prefix + key + suffix, value.toString());
            return this;
        }

        /** Create the final string. */
        public String build() {
            final Matcher matcher = pattern.matcher(baseString);
            if (strictMode && matcher.find()) {
                throw new MissingKeyException(matcher.group());
            } else {
                return baseString;
            }
        }

    }
}
