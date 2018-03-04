package com.jay.java8demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jay on 2017/4/8.
 * -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemory
 */
public class HeapOOM {

    static class OOMObject {}

    public static void main(String[] args) {
        List<OOMObject> objects = new ArrayList<>();
        while (true) {
            objects.add(new OOMObject());
        }
    }
}
