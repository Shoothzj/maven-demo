package com.github.shoothzj.demo.mariadb;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author hezhangjian
 */
@Slf4j
public class MariaLongDbTest {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String input = "mariadb://127.0.0.1:3306/1-234_poil-1890_zx0_~!@#$%^&*()=`[]{}|\\;:\"',./<>?-1234_QWELL-+1?useUnicode=true&characterEncoding=utf-8&user=hzj&password=Mysql@123&allowPublicKeyRetrieval=true";
        String url = new StringBuilder()
                .append("jdbc:")
                .append(input)
                .toString();
        Class.forName("org.mariadb.jdbc.Driver");
        Connection connection =  DriverManager.getConnection(url);
    }

}
