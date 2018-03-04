package com.jay.junit.framework;

/**
 * Created by jay on 16/7/31.
 */
public class ComparisonFailure extends AssertionFailedError {

    public static final int MAX_CONTENT_LENGTH = 20;

    private String expected;

    private String actual;

    public ComparisonFailure(String message, String expected, String actual) {
        super(message);
        this.expected = expected;
        this.actual = actual;
    }

    public String getMessage() {
        return new ComparisonCompactor(MAX_CONTENT_LENGTH, expected, actual).compact(super.getMessage());
    }

    public String getExpected() {
        return expected;
    }

    public String getActual() {
        return actual;
    }
}
