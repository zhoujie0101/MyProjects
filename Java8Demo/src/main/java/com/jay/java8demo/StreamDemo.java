package com.jay.java8demo;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by jay on 16/3/8.
 */
public class StreamDemo {
    public static void main(String[] agrs) {
        List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH)
        );

        List<String> nameList = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .map(Dish::getName)
                .limit(3)
                .collect(toList());
        System.out.println(nameList);

        List<String> nameList2 = menu.stream()
                .filter(d -> {
                    System.out.println("filtering " + d.getName());
                    return d.getCalories() > 300;
                })
                .map(d -> {
                        System.out.println("mapping " + d.getName());
                    return d.getName();
                })
                .limit(3)
                .collect(toList());

        List<String> wordsList = Arrays.asList("Java8", "Lambdas", "In", "Action");
        List<Integer> wordLengthList = wordsList.stream()
                .map(String::length)
                .collect(toList());
        System.out.println(wordLengthList);

        List<String[]> wordUniqueList = wordsList.stream()
                .map(word -> word.split(""))
                .collect(toList());
        System.out.println(wordUniqueList);
    }
}
