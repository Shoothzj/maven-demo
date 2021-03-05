package com.github.shoothzj.demo.sb.mongo.dao;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author hezhangjian
 */
@Slf4j
@Data
@Document(collection = "OrderHistory")
public class OrderHistoryPo {

    @Id
    private String orderId;

    @Field("payment")
    private double payment;

}
