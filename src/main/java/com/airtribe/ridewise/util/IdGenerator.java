package com.airtribe.ridewise.util;

public final class IdGenerator {
    private static long counter = 0;

    private IdGenerator() {
    }

    public static String nextId(String prefix) {
        counter++;
        return prefix + counter;
    }
}
