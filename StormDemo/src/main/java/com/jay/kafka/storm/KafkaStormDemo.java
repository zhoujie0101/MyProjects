package com.jay.kafka.storm;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.hdfs.bolt.HdfsBolt;
import org.apache.storm.hdfs.bolt.format.DefaultFileNameFormat;
import org.apache.storm.hdfs.bolt.format.DelimitedRecordFormat;
import org.apache.storm.hdfs.bolt.format.FileNameFormat;
import org.apache.storm.hdfs.bolt.format.RecordFormat;
import org.apache.storm.hdfs.bolt.rotation.FileRotationPolicy;
import org.apache.storm.hdfs.bolt.rotation.TimedRotationPolicy;
import org.apache.storm.hdfs.bolt.sync.CountSyncPolicy;
import org.apache.storm.hdfs.bolt.sync.SyncPolicy;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;

import java.util.UUID;

/**
 * Created by jay on 16/7/18.
 */
public class KafkaStormDemo {
    private static final String TOPOLOGY_NAME = "kafka-topology-hadoop-test-2";

    public static void main(String[] args) throws Exception {
        BrokerHosts hosts = new ZkHosts("localhost:2181,localhost:2182,localhost:2183", "/kafka/brokers");
        String topicName = "my-replicated-topic";
//        KafkaConfig kafkaConfig = new KafkaConfig(hosts, "my-replicated-topic");
        SpoutConfig spoutConfig = new SpoutConfig(hosts, topicName, "/storm", UUID.randomUUID().toString());
        spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
        KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);

        RecordFormat format = new DelimitedRecordFormat()
                .withFieldDelimiter("\t");
        SyncPolicy syncPolicy = new CountSyncPolicy(1);
        FileRotationPolicy policy = new TimedRotationPolicy(1.0f,
                TimedRotationPolicy.TimeUnit.MINUTES);
        FileNameFormat fileNameFormat = new DefaultFileNameFormat()
                .withPath("/tmp/").withPrefix("storm_").withExtension(".txt");
        HdfsBolt hdfsBolt = new HdfsBolt()
                .withFsUrl("hdfs://localhost")
                .withFileNameFormat(fileNameFormat)
                .withRecordFormat(format)
                .withRotationPolicy(policy)
                .withSyncPolicy(syncPolicy);
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("kafka_spout", kafkaSpout);
        builder.setBolt("emit_bolt", new EmitBolt()).shuffleGrouping("kafka_spout");
        builder.setBolt("kafka_bolt", hdfsBolt).globalGrouping("emit_bolt");

        Config config = new Config();
//        LocalCluster cluster = new LocalCluster();
//        cluster.submitTopology(TOPOLOGY_NAME, config, builder.createTopology());
        StormSubmitter.submitTopology(TOPOLOGY_NAME, config, builder.createTopology());
//        Utils.sleep(100000);
//        cluster.killTopology(TOPOLOGY_NAME);
//        cluster.shutdown();
    }
}
