package de.chrthms.mco.impl;

import de.chrthms.mco.MicroProcessEngine;
import de.chrthms.mco.MicroProcessEngineFactory;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;

/**
 * Created by christian on 18.05.17.
 */
public class MicroProcessEngineFactoryImpl implements MicroProcessEngineFactory {

    private String driver = null;
    private String url = null;
    private String username = null;
    private String password = null;

    @Override
    public MicroProcessEngineFactory jdbcDriver(String driver) {
        return null;
    }

    @Override
    public MicroProcessEngineFactory jdbcUrl(String url) {
        return null;
    }

    @Override
    public MicroProcessEngineFactory jdbcUsername(String username) {
        return null;
    }

    @Override
    public MicroProcessEngineFactory jdbcPassword(String password) {
        return null;
    }

    @Override
    public MicroProcessEngine build() {

        ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration()
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
                .setJdbcDriver(driver)
                .setJdbcUrl(url)
                .setJdbcUsername(username)
                .setJdbcPassword(password)
                .setHistory(ProcessEngineConfiguration.HISTORY_AUTO)
                .setJobExecutorActivate(Boolean.TRUE)
                .buildProcessEngine();

        return new MicroProcessEngineImpl(processEngine);
    }
}
