package org.mylitespring.util;

public abstract class Assert {
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notBlank(String string, String message) {
        if (!StringUtils.hasText(string)) {
            throw new IllegalArgumentException(message);
        }
    }
}
