package com.github.shoothzj.demo.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * @author hezhangjian
 */
@Slf4j
public class LinkedListTest {

    private static final LinkedList<String> list = new LinkedList<>();

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                list.add("111");
            }
        }).start();
        while (true) {
            try {
                System.out.println(list);
            } catch (Throwable e) {
                System.out.println(e.toString());
            }
        }
    }

}
