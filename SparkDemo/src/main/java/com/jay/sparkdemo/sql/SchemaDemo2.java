package com.jay.sparkdemo.sql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jay on 16/10/16.
 */
public class SchemaDemo2 {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("SchemaDemo");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(jsc);

        String schemaStr = "name age";
        List<StructField> fieldList = new ArrayList<>();
        for(String field : schemaStr.split(" ")) {
            fieldList.add(new StructField(field, DataTypes.StringType, true, Metadata.empty()));
        }
//        StructType type = new StructType(fields.toArray(new StructField[fields.size()]));
        StructType type = DataTypes.createStructType(fieldList);

        JavaRDD<Row> people = jsc
                .textFile("/Users/jay/project/idealProject/MyProjects/SparkDemo/target/classes/person.txt")
                .map(line -> {
                    String[] fields = line.split(",");
                    return RowFactory.create(fields[0], fields[1].trim());
                });
        DataFrame df = sqlContext.createDataFrame(people, type);
        df.registerTempTable("people");

        DataFrame teenagers = sqlContext.sql("SELECT name FROM people WHERE age >= 13 AND age <= 19");
        List<String> teenagerNames = teenagers.javaRDD().map(
                row -> "Name: " + row.getString(0)
        ).collect();
        System.out.println(teenagerNames);
    }
}
