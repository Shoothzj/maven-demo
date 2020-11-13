package com.github.shoothzj.spring.async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hezhangjian
 */
@Slf4j
@Service
public class UnderService {

    public String findOne() {
        return "one";
    }

    public List<String> findList() {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        return list;
    }

}
