package com.jay.junit.framework;

/**
 * A Test can be run and collect it result
 */
public interface Test {

    /**
     * count the number of test cases
     * @return
     */
    int countTestCase();

    /**
     * run a test and collect its result in a TestResult instance
     */
    void run(TestResult result);
}
