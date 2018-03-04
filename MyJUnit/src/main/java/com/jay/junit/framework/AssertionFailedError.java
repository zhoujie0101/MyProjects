package com.jay.junit.framework;

/**
 * Created by jay on 16/7/31.
 */
public class AssertionFailedError extends Error {

    private static final long serialVersionUID = 2863887327999177803L;

    public AssertionFailedError() {
    }

    public AssertionFailedError(String message) {
        super(defaultMessage(message));
    }

    private static String defaultMessage(String message) {
        return message == null ? "" : message;
    }
}
