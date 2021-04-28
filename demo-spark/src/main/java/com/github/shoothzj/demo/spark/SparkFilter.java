package com.github.shoothzj.demo.spark;

import com.google.common.io.Resources;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

import java.net.URL;

/**
 * @author hezhangjian
 */
@Slf4j
public class SparkFilter {

    public static void main(String[] args) {
        final URL url = Resources.getResource("data/simple_text.txt");
        String path = url.getPath();
        SparkSession spark = SparkSession.builder().master("local").appName("Simple Application").getOrCreate();
        Dataset<String> logData = spark.read().textFile(path).cache();

        long numAs = logData.filter((FilterFunction<String>) s -> s.contains("a")).count();
        long numBs = logData.filter((FilterFunction<String>) s -> s.contains("b")).count();

        System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);

        spark.stop();
    }

}
