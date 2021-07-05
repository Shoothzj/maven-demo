package com.github.shoothzj.demo.sb.cassandra.db.repo;

import com.github.shoothzj.demo.sb.cassandra.db.dao.Game;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hezhangjian
 */
@Repository
public interface BookRepository extends CassandraRepository<Game, String> {
}
