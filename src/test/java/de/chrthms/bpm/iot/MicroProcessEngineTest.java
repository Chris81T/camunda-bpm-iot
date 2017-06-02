package de.chrthms.bpm.iot;

import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.ProcessEngine;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by christian on 28.05.17.
 */
public class MicroProcessEngineTest {

    private final String driver = "org.h2.Driver";
    private final String url = "jdbc:h2:mem:test";
    private final String username = "sa";
    private final String password = "sa";


    @Test
    public void testBpmPlatformMicroEngine() {

        MicroProcessEngine microProcessEngine = MicroProcessEngineFactory.getInstance()
                .jdbcDriver(driver)
                .jdbcUrl(url)
                .jdbcUsername(username)
                .jdbcPassword(password)
                .build();

        ProcessEngine defaultProcessEngine = BpmPlatform.getDefaultProcessEngine();

        Assert.assertEquals(microProcessEngine, defaultProcessEngine);

        defaultProcessEngine.close();

    }

    @Test
    public void testCreateEngineWithSpecialName() throws InterruptedException {

        final String engineName = "customEngine";

        MicroProcessEngine microProcessEngine = MicroProcessEngineFactory.getInstance()
                .jdbcDriver(driver)
                .jdbcUrl(url)
                .jdbcUsername(username)
                .jdbcPassword(password)
                .engineName(engineName)
                .build();

        Assert.assertNull(BpmPlatform.getDefaultProcessEngine());

        Assert.assertEquals(microProcessEngine, BpmPlatform.getProcessEngineService().getProcessEngine(engineName));

        microProcessEngine.close();
    }

    @Test
    public void testCreateTwoEnginesWithSpecialNames() {

        final String engineName = "customEngineTWin1";
        final String engineNameTwo = "customEngineTWin2";

        MicroProcessEngine microProcessEngine = MicroProcessEngineFactory.getInstance()
                .jdbcDriver(driver)
                .jdbcUrl(url)
                .jdbcUsername(username)
                .jdbcPassword(password)
                .engineName(engineName)
                .build();

        MicroProcessEngine microProcessEngine2 = MicroProcessEngineFactory.getInstance()
                .jdbcDriver(driver)
                .jdbcUrl(url)
                .jdbcUsername(username)
                .jdbcPassword(password)
                .engineName(engineNameTwo)
                .build();

        Assert.assertNull(BpmPlatform.getDefaultProcessEngine());

        Assert.assertEquals(microProcessEngine, BpmPlatform.getProcessEngineService().getProcessEngine(engineName));
        Assert.assertEquals(microProcessEngine2, BpmPlatform.getProcessEngineService().getProcessEngine(engineNameTwo));

        microProcessEngine.close();
        microProcessEngine2.close();
    }

    /**
     * This test checks, that the micro-engine will unregistered at the BPMPlatform, if the engine will be closed.
     */
    @Test
    public void testCreateTwoEnginesWithSameNameSequentially() {

        final String engineName = "customEngine";

        MicroProcessEngine microProcessEngine = MicroProcessEngineFactory.getInstance()
                .jdbcDriver(driver)
                .jdbcUrl(url)
                .jdbcUsername(username)
                .jdbcPassword(password)
                .engineName(engineName)
                .build();

        Assert.assertNull(BpmPlatform.getDefaultProcessEngine());
        Assert.assertEquals(microProcessEngine, BpmPlatform.getProcessEngineService().getProcessEngine(engineName));
        microProcessEngine.close();

        MicroProcessEngine microProcessEngine2 = MicroProcessEngineFactory.getInstance()
                .jdbcDriver(driver)
                .jdbcUrl(url)
                .jdbcUsername(username)
                .jdbcPassword(password)
                .engineName(engineName)
                .build();

        Assert.assertNull(BpmPlatform.getDefaultProcessEngine());
        Assert.assertEquals(microProcessEngine2, BpmPlatform.getProcessEngineService().getProcessEngine(engineName));
        microProcessEngine2.close();

    }

}
