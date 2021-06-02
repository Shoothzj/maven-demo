package com.github.shoothzj.demo.avro;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.junit.Test;

/**
 * @author hezhangjian
 */
@Slf4j
public class SchemaTest {

    @Test
    public void schemaBuild() {
        final Schema record = SchemaBuilder.record("PulsarInstance").namespace("com.github.shoothzj.demo.avro")
                .fields().requiredString("cluster").requiredString("url").endRecord();
        log.info("record schema is {}", record);
    }

}
