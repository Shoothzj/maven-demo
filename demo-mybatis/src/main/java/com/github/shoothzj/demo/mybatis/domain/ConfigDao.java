package com.github.shoothzj.demo.mybatis.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

/**
 * @author hezhangjian
 */
@Data
@Entity
@Table(name = "Config", uniqueConstraints = @UniqueConstraint(columnNames = {"configGroup", "configName"}))
public class ConfigDao implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "config_group", length = 64)
    private String configGroup;

    @Column(name = "config_name", length = 64)
    private String configName;

    @Column(name = "config_schema", columnDefinition = "TEXT", length = 2048)
    private String config_schema;

    private int version;

    public ConfigDao() {
    }
}
