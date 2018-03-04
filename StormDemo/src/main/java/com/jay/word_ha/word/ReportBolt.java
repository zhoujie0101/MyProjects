package com.jay.word_ha.word;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jay on 16/7/17.
 */
public class ReportBolt extends BaseRichBolt {

    private Map<String, Long> counter;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        counter = new HashMap<>();
    }

    @Override
    public void execute(Tuple input) {
        String word = input.getStringByField("word");
        Long count = input.getLongByField("count");
        counter.put(word, count);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

    @Override
    public void cleanup() {
        System.out.println("--- FINAL COUNTS ---");
        List<String> keys = new ArrayList<>();
        keys.addAll(counter.keySet());
        Collections.sort(keys);
        for(String key : keys) {
            System.out.println(key + " : "  + counter.get(key));
        }
        System.out.println("--- FINAL COUNTS ---");

        super.cleanup();
    }
}
