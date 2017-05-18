package de.chrthms.mco.impl;

import de.chrthms.mco.MicroProcessEngine;
import de.chrthms.mco.MicroProcessEngineFactory;
import de.chrthms.mco.exceptions.McoRuntimeException;
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

    private boolean isValuePresent(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private boolean isValueEmpty(String value) {
        return !isValuePresent(value);
    }

    @Override
    public MicroProcessEngineFactory jdbcDriver(String driver) {
        this.driver = driver;
        return this;
    }

    @Override
    public MicroProcessEngineFactory jdbcUrl(String url) {
        this.url = url;
        return this;

    }

    @Override
    public MicroProcessEngineFactory jdbcUsername(String username) {
        this.username = username;
        return this;

    }

    @Override
    public MicroProcessEngineFactory jdbcPassword(String password) {
        this.password = password;
        return this;

    }

    @Override
    public MicroProcessEngine build() throws McoRuntimeException {

        if (isValueEmpty(driver) || isValueEmpty(url)) {
            throw new McoRuntimeException(new StringBuilder()
                .append("Missing information to build micro process-engine. At least driver = '")
                .append(driver)
                .append("' and url = '")
                .append(url)
                .append("' must be set.")
                .toString());
        }

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
