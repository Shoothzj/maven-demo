package com.github.shoothzj.demo.iot.mdevice.manage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.shoothzj.demo.iot.mdevice.manage.domain.RegisterDevicePO;

import org.springframework.stereotype.Repository;

@Repository
public interface RegisterDeviceRepository extends JpaRepository<RegisterDevicePO, String> {


}
