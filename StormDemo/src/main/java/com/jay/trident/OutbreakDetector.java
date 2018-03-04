package com.jay.trident;

import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jay on 16/7/20.
 */
public class OutbreakDetector extends BaseFunction {
    public static final int THRESHOLD = 10000;
    private static final long serialVersionUID = 950322258330058209L;

    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
        String key = (String) tuple.getValue(0);
        Long count = (Long) tuple.getValue(1);
        if (count > THRESHOLD) {
            List<Object> values = new ArrayList<>();
            values.add("Outbreak detected for [" + key + "]!");
            collector.emit(values);
        }
    }
}
