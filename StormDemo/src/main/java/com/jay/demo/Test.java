package com.jay.demo;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

/**
 * Created by jay on 2017/2/26.
 */
public class Test {
    public static void main(String[] args) {
        Config conf = new Config();
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new TestSpout());
        builder.setBolt("bolt", new TestBolt())
                .shuffleGrouping("spout");

        LocalCluster lc = new LocalCluster();
        lc.submitTopology("test", conf, builder.createTopology());
        lc.shutdown();
    }
}
