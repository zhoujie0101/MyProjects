package com.jay.java8demo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jay on 16/3/7.
 */
public class LambdaDemo {
    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(new Apple(80,"green"), new Apple(155, "green"), new Apple(120, "red"));

        //1.common use
        inventory.sort(new AppleComparator());
        inventory.forEach(System.out::println);
        System.out.println();

        //2.use anonymous function
        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });
        inventory.forEach(System.out::println);
        System.out.println();

        //3.use lambda
        inventory.sort((Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));
        //inventory.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
        inventory.forEach(System.out::println);
        System.out.println();

        //4.Comparator static helper method
        inventory.sort(Comparator.comparing((a) -> a.getWeight()));
        inventory.forEach(System.out::println);
        System.out.println();

        //5.use method reference
        inventory.sort(Comparator.comparing(Apple::getWeight));
        inventory.forEach(System.out::println);
        System.out.println();
    }

    public static class AppleComparator implements Comparator<Apple> {
        @Override
        public int compare(Apple o1, Apple o2) {
            return o1.getWeight().compareTo(o2.getWeight());
        }
    }
}
