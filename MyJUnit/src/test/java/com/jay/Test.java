package com.jay;

import junit.framework.TestCase;

/**
 * Created by jay on 16/7/31.
 */
public class Test extends TestCase {
    public static void main(String[] args) {
        Test test = new Test();
        System.out.println(test.getClass().asSubclass(TestCase.class));
    }
}
