package com.api.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {

    private DateTimeUtil() {
        // Private constructor to prevent instantiation
    }

    // Returns the current time in ISO-8601 format as a String with days subtracted
    // we require this for setting past dates in tests
    public static String getTimeWithDaysAgo(int daysAgo) {
        return Instant.now().minus(daysAgo, ChronoUnit.DAYS).toString();
    }
}
