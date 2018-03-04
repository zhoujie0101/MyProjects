package com.jay.junit.framework;


/**
 * Created by jay on 16/7/31.
 */
public abstract class TestCase implements Test {

    private String name;

    public TestCase() {
        name = null;
    }

    public TestCase(String name) {
        this.name = name;
    }

    @Override
    public int countTestCase() {
        return 1;
    }

    public TestResult run() {
        TestResult result = createResult();
        run(result);
        return result;
    }

    private TestResult createResult() {
        return new TestResult();
    }

    @Override
    public void run(TestResult result) {
        result.run(this);
    }

    public void runBare() throws Throwable {
        Throwable exception = null;
        setUp();

        try {
            runTest();
        } catch (Throwable t) {
            exception = t;
        } finally {
            try {
                tearDown();
            } catch (Throwable t) {
                if(exception == null) {
                    exception = t;
                }
            }
        }

        if (exception != null) {
            throw exception;
        }
    }

    private void runTest() {

    }

    protected void setUp() {

    }

    protected void tearDown() {

    }
}
