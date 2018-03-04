package com.jay.java8demo;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by jay on 16/3/3.
 */
public class ComsumeDemo {
    public static void main(String[] agrs) {
        forEach(Arrays.asList(1, 2, 3, 4 , 5), integer -> System.out.print(integer));
        forEach(Arrays.asList(1, 2, 3, 4 , 5), System.out::print);

        List<Integer> list = Arrays.asList(1, 2, 3);
        list.forEach(integer -> System.out.print(integer));
        list.forEach(System.out::print);
    }

    public static <T> void forEach(List<T> list, Consumer<T> consumer) {
        for(T t : list) {
            consumer.accept(t);
        }
    }
}
