package com.jay.java8demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jay on 16/2/26.
 */
public class PredicateDemo {

    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(new Apple(80,"green"), new Apple(155, "green"), new Apple(120, "red"));

        List<Apple> result = filter(inventory, (Apple apple) -> "red".equals(apple.getColor()));
        List<Apple> result2 = filter(inventory, apple -> "green".equals(apple.getColor()));
        System.out.println(result);
        System.out.println(result2);
        inventory.sort((Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));
        System.out.println(inventory);
    }
    public static List<Apple> filter(List<Apple> inventory, ApplePredicate p){
        List<Apple> result = new ArrayList<>();
        for(Apple apple : inventory){
            if(p.test(apple)){
                result.add(apple);
            }
        }
        return result;
    }

    interface ApplePredicate {
        boolean test(Apple apple);
    }

    static class AppleWeightPredicate implements ApplePredicate{
        public boolean test(Apple apple){
            return apple.getWeight() > 150;
        }
    }
    static class AppleColorPredicate implements ApplePredicate{
        public boolean test(Apple apple){
            return "green".equals(apple.getColor());
        }
    }

    static class AppleRedAndHeavyPredicate implements ApplePredicate{
        public boolean test(Apple apple){
            return "red".equals(apple.getColor()) && apple.getWeight() > 150;
        }
    }
}