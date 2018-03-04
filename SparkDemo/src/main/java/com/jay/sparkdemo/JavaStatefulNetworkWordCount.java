package com.jay.sparkdemo;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.Function3;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.State;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Created by jay on 16/10/9.
 */
public class JavaStatefulNetworkWordCount {
    private static final Pattern PATTERN = Pattern.compile(" ");

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("JavaStatefulNetworkWordCount");
        JavaStreamingContext jsc = new JavaStreamingContext(conf, Durations.seconds(1L));
        jsc.checkpoint(".");

        List<Tuple2<String, Integer>> initialData = Arrays.asList(
                new Tuple2<>("hello", 1),
                new Tuple2<>("world", 2));
        JavaPairRDD<String, Integer> initialRDD = jsc.sparkContext().parallelizePairs(initialData);

        JavaReceiverInputDStream<String> lines = jsc.socketTextStream("localhost", 9999,
                StorageLevel.MEMORY_AND_DISK_SER_2());
        JavaDStream<String> words = lines.flatMap(line -> Arrays.asList(PATTERN.split(line)));
        JavaPairDStream<String, Integer> pairs = words.mapToPair(word -> new Tuple2<>(word, 1));

        Function3<String, Optional<Integer>, State<Integer>, Tuple2<String, Integer>> mappingFunc =
            (word, one, state) -> {
                int sum = one.orElse(0) + (state.exists() ? state.get() : 0);
                Tuple2<String, Integer> tuple2 = new Tuple2<>(word, sum);
                state.update(sum);
                return tuple2;
            };
//        JavaMapWithStateDStream<String, Integer, State<Integer>, Tuple2<String, Integer>> stateDStream =
//                pairs.mapWithState(StateSpec.function(mappingFunc).initialState(initialRDD));
//        stateDStream.print();
        jsc.start();
        jsc.awaitTermination();
    }
}
