package com.github.shoothzj.demo.command;

import com.beust.jcommander.Parameter;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hezhangjian
 */
@Setter
@Getter
public class OpsCommand {

    @Parameter(names = "--help", help = true)
    private boolean help;

}
