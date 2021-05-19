package com.github.shoothzj.demo;

import com.beust.jcommander.JCommander;
import com.github.shoothzj.demo.command.OpsCommand;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {
        final OpsCommand opsCommand = new OpsCommand();
        JCommander.newBuilder().addObject(opsCommand).build().parse(args);
    }

}
