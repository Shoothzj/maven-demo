package com.github.shoothzj.demo.sb.mongo.controller;

import com.github.shoothzj.demo.sb.mongo.dao.OrderHistoryPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hezhangjian
 */
@Slf4j
@RestController
public class OrderHistoryController {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveOrderHistory(List<OrderHistoryPo> orderHistoryPos) {
        mongoTemplate.insert(orderHistoryPos, OrderHistoryPo.class);
    }

    /**
     * 无序批量操作，无需等待之前操作完成
     * @param orderHistoryPos
     */
    public void saveOrderHistoryWithoutOrder(List<OrderHistoryPo> orderHistoryPos) {
        final BulkOperations ops = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, OrderHistoryPo.class);
        for (OrderHistoryPo orderHistoryPo : orderHistoryPos) {
            ops.insert(orderHistoryPos);
        }
        ops.execute();
    }

}
