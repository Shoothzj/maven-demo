package com.github.shoothzj.demo.avro;

import com.github.shoothzj.javatool.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.FileReader;
import org.apache.avro.generic.GenericDatumReader;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.function.Consumer;

/**
 * @author hezhangjian
 */
@Slf4j
public class AzureIotAvroReaderTest {

    @Test
    public void readAvro() throws Exception {
        LogUtil.configureLog();
        GenericDatumReader<Object> reader = new GenericDatumReader<Object>();
        try (FileReader<Object> fileReader = DataFileReader.openReader(new File("/Users/akka/Downloads/azure_iot_hub_04.avro"), reader)) {
            Schema schema = fileReader.getSchema();
            log.info("schema is {}", schema);
            fileReader.forEachRemaining(o -> log.info("object is {}", o));
        }
    }

}
