package com.jay.demo;

import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;

/**
 * Created by jay on 2017/2/26.
 */
public class MyFunction extends BaseFunction {
    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
        for (int i = 0; i < tuple.getInteger(0); i++) {
            collector.emit(new Values(i));
        }
    }
}
