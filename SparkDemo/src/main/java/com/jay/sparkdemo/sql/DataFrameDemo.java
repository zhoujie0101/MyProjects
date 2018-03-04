package com.jay.sparkdemo.sql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

/**
 * Created by jay on 16/10/16.
 */
public class DataFrameDemo {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("DataFrameDemo");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(jsc);

        DataFrame df = sqlContext.read().json("/Users/jay/project/idealProject/MyProjects/SparkDemo/target/classes/person.json");

        df.show();

        df.printSchema();

        df.select("name").show();

        df.select(df.col("name"), df.col("age").plus(1)).show();

        df.filter(df.col("age").gt(21)).show();

        df.groupBy(df.col("age")).count().show();
    }
}
