package com.github.shoothzj.demo.jcommander;

import com.beust.jcommander.DynamicParameter;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author example from github
 */
public class JCommanderTest {

    @Parameter
    public List<String> parameters = new ArrayList<>();

    @Parameter(names = {"-log", "-verbose"}, description = "Level of verbosity")
    public Integer verbose = 1;

    @Parameter(names = "-groups", description = "Comma-separated list of group names to be run")
    public String groups;

    @Parameter(names = "-debug", description = "Debug mode")
    public boolean debug = false;

    @DynamicParameter(names = "-D", description = "Dynamic parameters go here")
    public Map<String, String> dynamicParams = new HashMap<String, String>();

    @Test
    public void testJCommander() {
        final JCommanderTest jct = new JCommanderTest();
        String[] argv = { "-log", "2", "-groups", "unit1,unit2,unit3", "-debug", "-Doption=value", "a", "b", "c" };
        JCommander.newBuilder().addObject(jct).build().parse(argv);

        Assert.assertEquals(2, jct.verbose.intValue());
        Assert.assertEquals("unit1,unit2,unit3", jct.groups);
        Assert.assertTrue(jct.debug);
        Assert.assertEquals("value", jct.dynamicParams.get("option"));
        Assert.assertEquals(Arrays.asList("a", "b", "c"), jct.parameters);
    }

}
