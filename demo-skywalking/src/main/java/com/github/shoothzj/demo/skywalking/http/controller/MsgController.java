package com.github.shoothzj.demo.skywalking.http.controller;

import com.github.shoothzj.demo.skywalking.http.module.HttpReqDto;
import com.github.shoothzj.demo.skywalking.http.module.HttpRespDto;
import com.github.shoothzj.demo.skywalking.http.module.MsgType;
import com.github.shoothzj.demo.skywalking.kafka.producer.KafkaProducerService;
import com.github.shoothzj.demo.skywalking.pulsar.producer.PulsarProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

/**
 * @author hezhangjian
 */
@Slf4j
@RestController
public class MsgController {

    @PostMapping(path = "/msg", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpRespDto> msgIn(@RequestBody HttpReqDto httpReqDto) {
        log.info("http req dto is {}", httpReqDto);
        switch (httpReqDto.getMsgType()) {
            case PULSAR:
                PulsarProducerService.send(httpReqDto.getMsg());
                break;
            case KAFKA:
                KafkaProducerService.send(httpReqDto.getMsg());
                break;
            default:
                break;
        }
        final HttpRespDto httpRespDto = new HttpRespDto();
        httpRespDto.setProcessTime(Instant.now());
        return new ResponseEntity<>(httpRespDto, HttpStatus.OK);
    }

}
