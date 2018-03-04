package com.jay.rxjavademo;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by jay on 2017/8/27.
 */
public class RangeDemo {

    public static void main(String[] args) {
        log("before");

        Observable.range(5, 3)
                .subscribe(RangeDemo::log);

        log("after");
        System.out.println();

        Observable<Integer> ints = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                log("create");

                subscriber.onNext(5);
                subscriber.onNext(6);
                subscriber.onNext(7);
                subscriber.onCompleted();

                log("complete");
            }
        }).cache();

        log("Starting");
        ints.subscribe(i -> log("Element A: " + i));
        ints.subscribe(i -> log("Element B: " + i));
        log("Exit");
    }

    public static void log(Object msg) {
        System.out.println(Thread.currentThread().getName() + ": " + msg);
    }
}
