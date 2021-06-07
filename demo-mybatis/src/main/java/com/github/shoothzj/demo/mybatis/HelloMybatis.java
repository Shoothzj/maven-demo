package com.github.shoothzj.demo.mybatis;

import com.github.shoothzj.demo.mybatis.domain.ConfigPo;
import com.github.shoothzj.demo.mybatis.mapper.ConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

/**
 * @author shoothzj
 */
@Slf4j
public class HelloMybatis {

    public static void main(String[] args) {
        SqlSessionFactoryBuilder sessionFactoryBuilder = new SqlSessionFactoryBuilder();
        Environment environment = new Environment("production", new JdbcTransactionFactory(), HikariCPDataSource.getDs());
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(ConfigMapper.class);
        SqlSessionFactory sessionFactory = sessionFactoryBuilder.build(configuration);
        SqlSession sqlSession = sessionFactory.openSession();
        ConfigMapper configMapper = sqlSession.getMapper(ConfigMapper.class);
        {
            ConfigPo configPo = new ConfigPo();
            configPo.setConfigName("config_name");
            configPo.setVersion(1);
            configPo.setConfigSchema("schema");
            Integer result = configMapper.saveConfig(configPo);
            log.info("result is {}", result);
        }
        ConfigPo configPo = configMapper.selectConfigPo("config_name");
        log.info("config po is {}", configPo);
//        {
//            Integer result = configMapper.deleteConfigPo("config_name");
//            log.info("result is {}", result);
//        }
        sqlSession.close();
    }

}
