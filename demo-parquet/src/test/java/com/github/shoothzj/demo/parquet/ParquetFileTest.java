package com.github.shoothzj.demo.parquet;

import com.github.shoothzj.component.avro.SchemaUtil;
import com.github.shoothzj.component.util.PathUtil;
import org.apache.avro.Schema;
import org.junit.Test;

import java.io.File;

/**
 * @author hezhangjian
 */
public class ParquetFileTest {

    private static final String path = PathUtil.getResourcesPath() + File.separator + "test.parquet";

    @Test
    public void testWrite() {
        final Schema schema = SchemaUtil.acquirePulsarSchema();
        ParquetUtil.writeToParquet(path, ParquetUtil.getRecords(schema), schema);
    }

    @Test
    public void testRead() {
        ParquetUtil.readFromParquet(path);
    }

}