package com.github.shoothzj.demo.graphql.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {

    @PostMapping(path = "/echo")
    public String echo(@RequestBody String body) {
        return body;
    }

}
