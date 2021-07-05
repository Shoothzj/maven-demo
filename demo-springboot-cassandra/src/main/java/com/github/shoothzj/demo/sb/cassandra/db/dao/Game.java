package com.github.shoothzj.demo.sb.cassandra.db.dao;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author hezhangjian
 */
@Table
@Data
public class Game {

    @PrimaryKeyColumn(name = "game_name", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private String gameName;

    @Column
    private Set<String> tags = new HashSet<>();

    public Game() {
    }

}
