package com.github.shoothzj.demo.sb.mongo.repo;

import com.github.shoothzj.demo.sb.mongo.dao.OrderHistoryPo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author hezhangjian
 */
public interface OrderHistoryRepository extends MongoRepository<OrderHistoryPo, String> {
}
