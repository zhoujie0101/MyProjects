package com.jay.java7demo;

import java.util.Random;

/**
 * Created by jay on 2017/4/8.
 * -XX:PermSize=10m -XX:MaxPermSize=10m
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) throws Exception {
        //CaseObject object = new CaseObject();
        while (true) {
            Random random = new Random();
            execute(random.nextInt(4000));

            //object.execute(random.nextInt(4000));
        }
    }
    public static Integer execute(int sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (Exception e) {
        }
        System.out.println("sleep time is=>"+sleepTime);
        return 0;
    }
}