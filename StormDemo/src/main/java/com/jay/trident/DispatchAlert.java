package com.jay.trident;

import com.esotericsoftware.minlog.Log;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;

/**
 * Created by jay on 16/7/20.
 */
public class DispatchAlert extends BaseFunction {

    private static final long serialVersionUID = -8928951222297954035L;

    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
        String alert = (String) tuple.getValue(0);
        Log.error("ALERT RECEIVED [" + alert + "]");
        Log.error("Dispatch the national guard!");
        System.exit(0);
    }
}
