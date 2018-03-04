package com.jay.java8demo;

/**
 * Created by jay on 2017/4/8.
 * -Xss64k
 */
public class JvmStackSOF {
    private int length = 0;
    private void test() {
        length++;
        test();
    }

    public static void main(String[] args) {
        JvmStackSOF obj = new JvmStackSOF();
        try {
            obj.test();
        } catch (Throwable t) {
            System.out.println("length:" + obj.length);
            throw t;
        }
    }
}
