package com.github.shoothzj.demo.mybatis.mapper;

import com.github.shoothzj.demo.mybatis.domain.ConfigPo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author shoothzj
 */
public interface ConfigMapper {

    @Insert("Insert into config (config_name, config_schema, version) values (#{configName}, #{configSchema}, #{version})")
    Integer saveConfig(ConfigPo configPo);

    @Select("SELECT * FROM config WHERE config_name = #{config_name}")
    @Results(value = { @Result(property = "configName", column = "config_name"),
            @Result(property = "configSchema", column = "config_schema"),
            @Result(property = "version", column = "version")})
    ConfigPo selectConfigPo(@Param("config_name") String configName);

    @Delete("DELETE FROM config WHERE config_name = #{config_name}")
    Integer deleteConfigPo(@Param("config_name") String configName);

}
