package com.jay.trident;

import org.apache.storm.trident.state.map.NonTransactionalMap;

/**
 * Created by jay on 16/7/20.
 */
public class OutbreakTrendState extends NonTransactionalMap<Long> {

    protected OutbreakTrendState(OutbreakTrendBackingMap outbreakBackingMap) {
        super(outbreakBackingMap);
    }
}
