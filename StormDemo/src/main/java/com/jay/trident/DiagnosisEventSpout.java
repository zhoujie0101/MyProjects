package com.jay.trident;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.trident.spout.ITridentSpout;
import org.apache.storm.tuple.Fields;

import java.util.Map;

/**
 * Created by jay on 16/7/20.
 */
public class DiagnosisEventSpout implements ITridentSpout<Long> {

    BatchCoordinator<Long> coordinator = new DiagnosisEventCoordinator();
    Emitter emitter = new DiagnosisEventEmmitter();

    @Override
    public BatchCoordinator<Long> getCoordinator(String txStateId, Map conf, TopologyContext context) {
        return coordinator;
    }

    @Override
    public Emitter<Long> getEmitter(String txStateId, Map conf, TopologyContext context) {
        return emitter;
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

    @Override
    public Fields getOutputFields() {
        return new Fields("event");
    }
}
