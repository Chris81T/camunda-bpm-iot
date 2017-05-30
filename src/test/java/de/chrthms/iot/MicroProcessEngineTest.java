package de.chrthms.iot;

import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.ProcessEngine;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by christian on 28.05.17.
 */
public class MicroProcessEngineTest {

// TODO Problems while no own jvm execution
//    @Test
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

        defaultProcessEngine.close();

    }

// TODO Problems while no own jvm execution
//    @Test
    public void testCreateEngineWithSpecialName() throws InterruptedException {

        final String driver = "org.h2.Driver";
        final String url = "jdbc:h2:mem:test";
        final String username = "sa";
        final String password = "sa";

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

        final String driver = "org.h2.Driver";
        final String url = "jdbc:h2:mem:test";
        final String username = "sa";
        final String password = "sa";

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

}
