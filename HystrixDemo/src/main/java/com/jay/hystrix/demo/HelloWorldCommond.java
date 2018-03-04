package com.jay.hystrix.demo;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by jay on 2017/8/26.
 */
public class HelloWorldCommond extends HystrixCommand<String> {
    private String name;

    protected HelloWorldCommond(String name) {
        super(HystrixCommandGroupKey.Factory.asKey(name));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        return "Hello, " + name + "!";
    }
}
