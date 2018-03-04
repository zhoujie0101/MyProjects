package com.jay.sparkdemo;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

/**
 * Created by jay on 16/9/15.
 */
public class SimpleApp {
    public static void main(String[] args) {
        String logFile = "/usr/local/spark-1.6.1-bin-hadoop2.6/README.md";
        SparkConf conf = new SparkConf().setAppName("Simple App");

        JavaSparkContext sc = new JavaSparkContext("spark://jay-macbook-pro:7077", "SimpleApp");
        JavaRDD<String> logData = sc.textFile(logFile).cache();

        long numAs = logData.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String line) throws Exception {
                return line.contains("a");
            }
        }).count();

        long numBs = logData.filter(line -> line.contains("b")).count();

        System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);
    }
}
