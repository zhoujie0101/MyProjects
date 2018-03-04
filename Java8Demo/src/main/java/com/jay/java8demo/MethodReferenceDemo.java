package com.jay.java8demo;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jay on 16/3/7.
 */
public class MethodReferenceDemo {
    public static void main(String[] agrs) {
        List<String> strList = Arrays.asList("B", "a", "c", "D");
        strList.sort(String::compareToIgnoreCase);
        strList.forEach(System.out::print);
    }
}
