package com.github.shoothzj.demo.parquet;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hezhangjian
 */
@Slf4j
public class ParquetUtil {

    public static List<GenericData.Record> getRecords(Schema schema) {
        final ArrayList<GenericData.Record> records = new ArrayList<>();
        final GenericData.Record record = new GenericData.Record(schema);
        // add two records
        record.put("cluster", "cluster1");
        record.put("url", "pulsar1.com");
        records.add(record);

        record.put("cluster", "cluster2");
        record.put("url", "pulsar2.com");
        records.add(record);

        return records;
    }

    @SneakyThrows
    public static void writeToParquet(String filePath, List<GenericData.Record> records, Schema schema) {
        final Path path = new Path(filePath);
        final ParquetWriter<Object> writer = AvroParquetWriter.builder(path).withRowGroupSize(ParquetWriter.DEFAULT_BLOCK_SIZE)
                .withPageSize(ParquetWriter.DEFAULT_PAGE_SIZE).withSchema(schema)
                .withConf(new Configuration()).withCompressionCodec(CompressionCodecName.SNAPPY)
                .withValidation(false).withDictionaryEncoding(false).build();
        for (GenericData.Record record : records) {
            writer.write(record);
        }
        writer.close();
    }

    @SneakyThrows
    public static void readFromParquet(String filePath) {
        final Path path = new Path(filePath);
        final ParquetReader<Object> reader = AvroParquetReader.builder(path).withConf(new Configuration()).build();
        while (true) {
            final Object read = reader.read();
            if (read == null) {
                break;
            }
            log.info("class is {}", read.getClass());
            log.info("record is {}", read);
        }
    }

}
