package com.jay.word_ha.word;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by jay on 16/7/17.
 */
public class SentenceSpout extends BaseRichSpout {

    private String[] sentences = {
            "the cow jumped over the moon",
            "an apple a day keeps the doctor away",
            "four score and seven years ago",
            "snow white and the seven dwarfs",
            "i am at two with nature"
    };

    private SpoutOutputCollector collector;

    private ConcurrentMap<UUID, Values> pending;

    private int index = 0;

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        collector = spoutOutputCollector;
        pending = new ConcurrentHashMap<>();
    }

    @Override
    public void nextTuple() {
        Values values = new Values(sentences[index++]);
        UUID msgId = UUID.randomUUID();
        collector.emit(values, msgId);
        pending.put(msgId, values);
        if(index >= sentences.length) {
            index = 0;
        }
        Utils.sleep(1);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("sentence"));
    }

    @Override
    public void ack(Object msgId) {
        UUID uuid = (UUID) msgId;
        pending.remove(uuid);
    }

    @Override
    public void fail(Object msgId) {
        collector.emit(pending.get(msgId), msgId);
    }
}
