package com.github.shoothzj.demo.agent.test.controller;

import com.github.shoothzj.demo.agent.test.module.rest.CreateJobRespDto;
import com.github.shoothzj.demo.agent.test.module.rest.CreateThingReqDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestThingController {

    /**
     * @param thingName
     * @param createThingReqDto
     * @return
     * https://docs.aws.amazon.com/iot/latest/apireference/API_CreateJob.html
     * curl -X POST -H 'Content-Type: application/json' 127.0.0.1:8080/things/111 -d '{"thingTypeName":"thingTypeName"}' -iv
     */
    @PostMapping(path = "/things/{thingName}")
    public ResponseEntity<CreateJobRespDto> createJob(@PathVariable("thingName") String thingName, @RequestBody CreateThingReqDto createThingReqDto) {
        throw new RuntimeException();
    }

}
