package com.github.shoothzj.component.avro;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;

/**
 * @author hezhangjian
 */
@Slf4j
public class SchemaUtil {

    public static Schema acquirePulsarSchema() {
        return SchemaBuilder.record("PulsarInstance").namespace("com.github.shoothzj.demo.avro")
                .fields().requiredString("cluster").requiredString("url").endRecord();
    }

}
