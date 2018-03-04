package com.jay.java8demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Created by jay on 16/3/3.
 */
public class FunctionDemo {
    public static void main(String[] agrs) {
        List<Integer> list = map(Arrays.asList(1, 2, 3), integer -> integer + 1);
        list.forEach(System.out::print);
//        List<String> strList = Arrays.asList("B", "a", "c", "D");
//        strList.sort(String::compareToIgnoreCase);
//        strList.forEach(System.out::print);
    }

    public static <T, R> List<R> map(List<T> list, Function<T, R> func) {
        List<R> result = new ArrayList<>();
        for(T t : list) {
            result.add(func.apply(t));
        }

        return result;
    }
}
