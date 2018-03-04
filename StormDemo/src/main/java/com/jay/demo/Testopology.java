package com.jay.demo;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;

/**
 * Created by jay on 16/7/20.
 */
public class Testopology {

    public static StormTopology buildTopology() {
        TridentTopology topology = new TridentTopology();

        TestSpout spout = new TestSpout();
        Stream stream = topology.newStream("event", spout);
        stream.each(new Fields("a"), new MyFunction(), new Fields("aaa"))
                .each(new Fields("aaa"), new MyFunction2(), new Fields("bbb"));

        return topology.build();
    }

    public static void main(String[] args) {
        Config conf = new Config();
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("test", conf, buildTopology());
        Utils.sleep(200000);
        cluster.shutdown();
    }
}
