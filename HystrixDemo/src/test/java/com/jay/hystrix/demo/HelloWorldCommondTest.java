package com.jay.hystrix.demo;

import org.junit.Test;
import rx.Observable;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * Created by jay on 2017/8/26.
 */
public class HelloWorldCommondTest {
    @Test
    public void testSync() {
        assertEquals("Hello, world!", new HelloWorldCommond("world").execute());
    }

    @Test
    public void testAsync() throws ExecutionException, InterruptedException {
        assertEquals("Hello, world!", new HelloWorldCommond("world").queue().get());
    }

    @Test
    public void testObserveable() {
        Observable<String> observable = new HelloWorldCommond("world").toObservable();
        assertEquals("Hello, world!", observable.toBlocking().single());

//        observable.subscribe(new Observer<String>() {
//            @Override
//            public void onCompleted() {
//                System.out.println("onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onNext(String s) {
//                System.out.println("onNext: " + s);
//            }
//        });

        observable.subscribe((v) -> {
            System.out.println("onNext: " + v);
        }, Throwable::printStackTrace, () -> {
            System.out.println("onCompleted");
        });
    }
}
