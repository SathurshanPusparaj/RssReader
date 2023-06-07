package org.projectx.rssreader.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeParser {

    public static LocalDateTime format(DateTimePattern dateTimePattern, String dateTime) {
        return format(dateTimePattern.pattern, dateTime);
    }

    public static LocalDateTime format(String pattern, String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateTime, formatter);
    }

    public enum DateTimePattern {
        FIRST("E, dd MMM yyyy HH:mm:ss z");

        String pattern;

        DateTimePattern(String pattern) {
            this.pattern = pattern;
        }
    }
}
