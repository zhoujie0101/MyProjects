package com.jay.rxjavademo;

import rx.Observable;

/**
 * Created by jay on 2017/8/26.
 */
public class HelloWorldExample {

    public static void main(String[] args) {
        hello("world", "zhangsan");
    }

    public static void hello(String... name) {
        Observable<String> observable = Observable.from(name);
        observable.subscribe(s -> {
            System.out.println("Hello, " + s + "!");
        });

        observable.toBlocking().forEach(System.out::println);
        System.out.println(System.currentTimeMillis());
    }
}
