package com.jay.rxjavademo;

import rx.Observable;
import rx.Subscription;

import java.math.BigInteger;

/**
 * Created by jay on 2017/8/27.
 */
public class InfiniteObservableDemo {

    public static void main(String[] args) {
//        Observable<BigInteger> narualNumbers =  Observable.create(new Observable.OnSubscribe<BigInteger>() {
//            @Override
//            public void call(Subscriber<? super BigInteger> subscriber) {
//                BigInteger bi = BigInteger.ZERO;
//                while (true) {
//                    bi = bi.add(BigInteger.ONE);
//                    subscriber.onNext(bi);
//                }
//            }
//        });

        Observable<BigInteger> naturalNumbers =  Observable.create(subscriber -> {
            BigInteger bi = BigInteger.ZERO;
            //don't do this
            while (true) {
                bi = bi.add(BigInteger.ONE);
                subscriber.onNext(bi);
            }
        });

        naturalNumbers.subscribe(System.out::println);

        Observable<BigInteger> naturalNumbers2 = Observable.create(subscriber -> {
            Runnable r = () -> {
                BigInteger bi = BigInteger.ZERO;
                while (!subscriber.isUnsubscribed()) {
                    bi = bi.add(BigInteger.ONE);
                    subscriber.onNext(bi);
                }
            };
            new Thread(r).start();
        });
        Subscription subscription = naturalNumbers2.subscribe(System.out::println);
        subscription.unsubscribe();
    }
}
