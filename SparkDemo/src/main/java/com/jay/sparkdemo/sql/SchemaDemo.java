package com.jay.sparkdemo.sql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

import java.util.List;

/**
 * Created by jay on 16/10/16.
 */
public class SchemaDemo {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("SchemaDemo");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(jsc);

        JavaRDD<Person> people = jsc
                .textFile("/Users/jay/project/idealProject/MyProjects/SparkDemo/target/classes/person.txt")
                .map(line -> {
                        String[] params = line.split(",");
                        return new Person(params[0], Integer.parseInt(params[1].trim()));
                   });
        DataFrame df = sqlContext.createDataFrame(people, Person.class);
        df.registerTempTable("people");

        DataFrame teenagers = sqlContext.sql("SELECT name FROM people WHERE age >= 13 AND age <= 19");
        List<String> teenagerNames = teenagers.javaRDD().map(
                row -> "Name: " + row.getString(0)
        ).collect();
        System.out.println(teenagerNames);
    }
}
