package com.jay.java7demo;

/**
 * Created by jay on 2017/4/9.
 */
public class TestClass {
    public static void main(String[] args) {
//        System.out.println(A.value);
        {
            byte[] bytes = new byte[64 * 1024 * 1024];
        }
        int a = 0;
        System.gc();
    }
}
