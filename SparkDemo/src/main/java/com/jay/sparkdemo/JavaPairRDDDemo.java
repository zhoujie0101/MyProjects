package com.jay.sparkdemo;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

/**
 * Created by jay on 16/9/16.
 */
public class JavaPairRDDDemo {
    public static void main(String[] args) {
        String logFile = "/usr/local/spark-1.6.1-bin-hadoop2.6/README.md";
        SparkConf conf = new SparkConf().setAppName("Simple App");

        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> logData = sc.textFile(logFile).cache();

        JavaPairRDD<String, Integer> pairs = logData.mapToPair(s -> new Tuple2<>(s, 1));
        JavaPairRDD<String, Integer> counts = pairs.reduceByKey((a, b) -> a + b);

        counts.foreach(t -> System.out.println(t._1() + ":" + t._2()));
    }
}
