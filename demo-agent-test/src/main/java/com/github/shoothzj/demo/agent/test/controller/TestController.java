package com.github.shoothzj.demo.agent.test.controller;

import com.github.shoothzj.demo.agent.test.module.rest.CreateJobReqDto;
import com.github.shoothzj.demo.agent.test.module.rest.CreateJobRespDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hezhangjian
 */
@Slf4j
@RestController
public class TestController {

    /**
     * @param jobId
     * @param createJobReqDto
     * @return
     */
    @PutMapping(path = "/jobs/{jobId}")
    public ResponseEntity<CreateJobRespDto> createJob(@PathVariable("jobId") String jobId, @RequestBody CreateJobReqDto createJobReqDto) {
        final CreateJobRespDto jobRespDto = new CreateJobRespDto();
        createJobReqDto.setDescription("description");
        createJobReqDto.setDocument("document");
        return new ResponseEntity<>(jobRespDto, HttpStatus.CREATED);
    }

}
