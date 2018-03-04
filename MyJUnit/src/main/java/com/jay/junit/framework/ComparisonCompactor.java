package com.jay.junit.framework;

/**
 * Created by jay on 16/7/31.
 */
public class ComparisonCompactor {

    public static final String ELLIPSIS = "...";
    public static final String DELTA_START = "[";
    public static final String DELTA_END = "]";

    private int contentLength;
    private String expected;
    private String actual;
    private int prefix;
    private int suffix;

    public ComparisonCompactor(int contentLength, String expected, String actual) {
        this.contentLength = contentLength;
        this.expected = expected;
        this.actual = actual;
    }

    public String compact(String message) {
        if(expected == null || actual == null || expected.equals(actual)) {
            return Assert.format(message, expected, actual);
        }

        findCommonPrefix();
        findCommonSuffix();

        String compactExpected = compactString(expected);
        String compactActual = compactString(actual);

        return Assert.format(message, compactExpected, compactActual);
    }

    private void findCommonPrefix() {
        prefix = 0;
        int end = Math.min(expected.length(), actual.length());
        for(; prefix < end; prefix++) {
            if(expected.charAt(prefix) != actual.charAt(prefix)) {
                break;
            }
        }
    }

    private void findCommonSuffix() {
        int expectedSuffix = expected.length() - 1;
        int actualSuffix = actual.length() - 1;
        for(; expectedSuffix >= prefix && actualSuffix >= prefix; expectedSuffix--, actualSuffix--) {
            if(expected.charAt(expectedSuffix) != actual.charAt(actualSuffix)) {
                break;
            }
        }
        suffix = expected.length() - expectedSuffix;
    }

    private String compactString(String str) {
        String result = DELTA_START + str.substring(prefix, str.length() - suffix + 1) + DELTA_END;
        if(prefix > 0) {
            result = computeCommonPrefix() + result;
        }
        if(suffix > 0) {
            result = result + computeCommonSuffix();
        }

        return result;
    }

    private String computeCommonPrefix() {
        return (prefix > contentLength ? ELLIPSIS : "") +
                expected.substring(Math.min(0, prefix - contentLength), prefix);
    }

    private String computeCommonSuffix() {
        int end = Math.min(expected.length() - suffix + 1 + contentLength, expected.length());
        return expected.substring(expected.length() - suffix + 1, end) +
                (expected.length() - suffix + 1 < expected.length() - contentLength ? ELLIPSIS : "");
    }
}
