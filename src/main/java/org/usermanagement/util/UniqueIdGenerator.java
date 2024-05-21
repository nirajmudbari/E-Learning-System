package org.usermanagement.util;

public class UniqueIdGenerator {

    private static long lastId = 0;

    public static synchronized String generateId() {
        long currentMillis = System.currentTimeMillis();
        if (currentMillis <= lastId) {
            currentMillis = lastId + 1;
        }
        lastId = currentMillis;
        return String.valueOf(currentMillis);
    }
}
