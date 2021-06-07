package com.github.shoothzj.demo.mybatis;

import com.github.shoothzj.component.mysql.MysqlConstant;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCPDataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl(MysqlConstant.JDBC_URL);
        ds = new HikariDataSource(config);
    }

    public static HikariDataSource getDs() {
        return ds;
    }
}