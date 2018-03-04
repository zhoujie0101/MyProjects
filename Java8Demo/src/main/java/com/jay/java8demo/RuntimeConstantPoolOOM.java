package com.jay.java8demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jay on 2017/4/8.
 * -XX:PermSize=10m -XX:MaxPermSize=10
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }
}
