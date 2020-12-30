package com.github.shoothzj.demo.agent.test.module.rest;

import lombok.Data;

/**
 * @author hezhangjian
 */
@Data
public class CreateJobReqDto {

    private String description;

    private String document;

    public CreateJobReqDto() {
    }

}
