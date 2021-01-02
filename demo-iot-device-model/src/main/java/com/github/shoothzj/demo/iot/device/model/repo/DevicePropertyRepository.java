package com.github.shoothzj.demo.iot.device.model.repo;

import com.github.shoothzj.demo.iot.device.model.dao.DevicePropertyDao;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author hezhangjian
 */
public interface DevicePropertyRepository extends MongoRepository<DevicePropertyDao, String> {
}
