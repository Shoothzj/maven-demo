package com.github.shoothzj.component.mysql;

/**
 * @author shoothzj
 */
public class MysqlConstant {

    public static final String DATABASE = "ttbb";

    public static final String USER = "hzj";

    public static final String PASSWORD = "Mysql@123";

    public static final String HOST = "localhost";

    public static final String JDBC_URL = String.format("jdbc:mysql://%s/%s?user=%s&password=%s", HOST, DATABASE, USER, PASSWORD);

}
