package com.jay.java8demo;

/**
 * Created by jay on 2018/2/19.
 */
public class Test {
    int chooseFar(int i) {
        switch (i) {
            case -100: return -1;
            case 0: return 0;
            case 100: return 1;
            default: return -1;
        }
    }

    void createThreadArray() {
        Thread t[];
        int count = 10;
        t = new Thread[count];
        t[0] = new Thread();
    }
}
