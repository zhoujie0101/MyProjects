package com.jay.junit.framework;

/**
 * Created by jay on 16/7/31.
 */
public class Assert {

    public static void assertTrue(boolean condition) {
        assertTrue(null, condition);
    }

    private static void assertTrue(String message, boolean condition) {
        if(!condition) {
            fail(message);
        }
    }

    public static void assertFalse(boolean condition) {
        assertFalse(null, condition);
    }

    public static void assertFalse(String message, boolean condition) {
        assertTrue(message, !condition);
    }

    public static void fail(String message) {
        if(message == null) {
            throw new AssertionFailedError();
        }

        throw new AssertionFailedError(message);
    }

    public static void assertEquals(Object expected, Object actual) {
        assertEquals(null, expected, actual);
    }

    public static void assertEquals(String message, Object expected, Object actual) {
        if(expected == null && actual == null) {
            return;
        }

        if(expected != null && expected.equals(actual)) {
            return;
        }

        failNotEquals(message, expected, actual);
    }

    private static void failNotEquals(String message, Object expected, Object actual) {
        fail(format(message, expected, actual));
    }

    public static void assertEquals(String message, String expected, String actual) {
        if(expected == null && actual == null) {
            return;
        }

        if(expected != null && expected.equals(actual)) {
            return;
        }

        String clearMessage = message == null ? "" : message;

        throw new ComparisonFailure(clearMessage, expected, actual);
    }

    public static String format(String message, Object expected, Object actual) {
        String formatted = "";
        if(message != null && message.length() > 0) {
            formatted = message + " ";
        }
        return formatted + "expected:<" + expected + "> but was:<" + actual + ">";
    }
}
