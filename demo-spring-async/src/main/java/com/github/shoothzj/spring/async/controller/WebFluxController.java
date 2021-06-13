package com.github.shoothzj.spring.async.controller;

import com.github.shoothzj.spring.async.service.UnderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author hezhangjian
 */
@Slf4j
@RestController
public class WebFluxController {

    @Autowired
    private UnderService underService;

    public Mono<ServerResponse> get() {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(BodyInserters.fromValue(underService.findOne()));
    }

    /**
     * @return
     */
    public Flux<String> getAll() {
        return Flux.fromIterable(underService.findList());
    }

}
