package com.jay.sparkdemo.stream;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.Arrays;

/**
 * Created by jay on 16/10/9.
 */
public class SparkStreamDemo {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount");
        JavaStreamingContext jsc = new JavaStreamingContext(conf, Durations.seconds(1L));
        JavaReceiverInputDStream<String> lines = jsc.socketTextStream("localhost", 9999);

        JavaDStream<String> words = lines.flatMap(s -> Arrays.asList(s.split(" ")));

        JavaPairDStream<String, Integer> pairs = words.mapToPair(s -> new Tuple2<>(s, 1));

        JavaPairDStream<String, Integer> wordCounts = pairs.reduceByKey((i1, i2) -> i1 + i2);

        wordCounts.print();

        jsc.start();
        jsc.awaitTermination();
    }
}
