package com.github.shoothzj.demo.agent.test.module.rest;

import lombok.Data;

/**
 * https://docs.aws.amazon.com/iot/latest/apireference/API_CreateJob.html
 * @author hezhangjian
 */
@Data
public class CreateJobRespDto {

    private String description;

    private String jobArn;

    private String jobId;

    public CreateJobRespDto() {
    }
}
