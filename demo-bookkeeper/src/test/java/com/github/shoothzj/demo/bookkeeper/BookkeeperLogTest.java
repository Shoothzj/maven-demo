package com.github.shoothzj.demo.bookkeeper;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class BookkeeperLogTest {

    @Test
    public void testParseLog() throws Exception {
        final BookkeeperLog bookkeeperLog = new BookkeeperLog();
        String logPath = "/Users/akka/ShootHzj/ec49.log";
        bookkeeperLog.scanLog(new File(logPath));
    }


}