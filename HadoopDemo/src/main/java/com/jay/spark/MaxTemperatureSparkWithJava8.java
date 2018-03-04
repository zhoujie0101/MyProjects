package com.jay.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

/**
 * Created by jay on 16/4/20.
 */
public class MaxTemperatureSparkWithJava8 {
    public static void main(String[] args) {
        if(args.length != 2) {
            System.err.println("Usage: MaxTemperatureSparkWithJava8 <input path> <output path>");
            System.exit(-1);
        }

        SparkConf conf = new SparkConf();
        JavaSparkContext sc = new JavaSparkContext("local", "Max temperature", conf);
        JavaRDD<String> lines =sc.textFile(args[0]);
        JavaRDD<String[]> records = lines.map((line) -> line.split("\t"));
        JavaRDD<String[]> filtered = records.filter(rec -> (!rec[1].equals("9999") && rec[2].matches("[01459]")));
        JavaPairRDD<Integer, Integer> tuples = filtered.mapToPair(rec ->
                new Tuple2<>(Integer.parseInt(rec[0]), Integer.parseInt(rec[1])));
        JavaPairRDD<Integer, Integer> maxTemps = tuples.reduceByKey(Math::max);
        maxTemps.saveAsTextFile(args[1]);
    }
}
