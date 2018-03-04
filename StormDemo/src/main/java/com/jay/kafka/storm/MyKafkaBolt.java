package com.jay.kafka.storm;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

/**
 * Created by jay on 16/7/17.
 */
public class MyKafkaBolt extends BaseRichBolt {

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {

    }

    @Override
    public void execute(Tuple input) {
        String msg = input.getString(0);
        System.out.println("---------------------" + msg + "----------------------");
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
