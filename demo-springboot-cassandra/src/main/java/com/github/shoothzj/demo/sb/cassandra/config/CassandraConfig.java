package com.github.shoothzj.demo.sb.cassandra.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

/**
 * @author hezhangjian
 */
@Slf4j
@EnableReactiveCassandraRepositories(basePackages = "com.github.shoothzj.demo.sb.cassandra.db.repo")
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Override
    protected String getKeyspaceName() {
        return "testKeySpace";
    }

}
