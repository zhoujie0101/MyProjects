package com.jay.rxjavademo;

import rx.Observable;

import java.util.concurrent.TimeUnit;

/**
 * Created by jay on 2017/8/27.
 */
public class DelayDemo {
    public static void main(String[] args) throws InterruptedException {
        Observable
                .just("Lorem", "ipsum", "dolor", "sit", "amet",
                        "consectetur", "adipiscing", "elit")
                .delay(word -> Observable.timer(word.length(), TimeUnit.SECONDS))
                .subscribe(System.out::println);

        TimeUnit.SECONDS.sleep(15);

        Observable
                .just("Lorem", "ipsum", "dolor", "sit", "amet",
                        "consectetur", "adipiscing", "elit")
                .flatMap(word -> Observable.timer(word.length(), TimeUnit.SECONDS).map(x -> word));
    }
}
