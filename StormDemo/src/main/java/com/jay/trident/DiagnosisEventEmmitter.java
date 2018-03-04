package com.jay.trident;

import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.spout.ITridentSpout;
import org.apache.storm.trident.topology.TransactionAttempt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jay on 16/7/20.
 */
public class DiagnosisEventEmmitter implements ITridentSpout.Emitter, Serializable {

    private static final long serialVersionUID = 8688045267516430834L;

    private AtomicInteger successfulTransactions = new AtomicInteger(0);

    @Override
    public void emitBatch(TransactionAttempt tx, Object coordinatorMeta, TridentCollector collector) {
        for(int i = 0; i < 10000; i++) {
            List<Object> events = new ArrayList<>();
            double lat = (double) (-30 + (int) (Math.random() * 75));  //-30 ~ 45
            double lng = (double) (-120 + (int) (Math.random() * 70));  //-120 ~ -50
            long time = System.currentTimeMillis();
            String diag = Integer.toString(320 + (int) (Math.random() * 7)); //320 ~ 327
            DiagnosisEvent event = new DiagnosisEvent(lat, lng, time, diag);
            events.add(event);

            collector.emit(events);
        }
    }

    @Override
    public void success(TransactionAttempt tx) {
        successfulTransactions.incrementAndGet();
    }

    @Override
    public void close() {

    }
}
