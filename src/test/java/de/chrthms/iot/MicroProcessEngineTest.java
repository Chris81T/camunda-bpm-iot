package de.chrthms.iot;

import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.ProcessEngine;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by christian on 28.05.17.
 */
public class MicroProcessEngineTest {


    @Test
    public void testBpmPlatformMicroEngine() {

        final String driver = "org.h2.Driver";
        final String url = "jdbc:h2:mem:test";
        final String username = "sa";
        final String password = "sa";

        MicroProcessEngine microProcessEngine = MicroProcessEngineFactory.getInstance()
                .jdbcDriver(driver)
                .jdbcUrl(url)
                .jdbcUsername(username)
                .jdbcPassword(password)
                .build();

        ProcessEngine defaultProcessEngine = BpmPlatform.getDefaultProcessEngine();

        Assert.assertEquals(microProcessEngine, defaultProcessEngine);

    }

}
