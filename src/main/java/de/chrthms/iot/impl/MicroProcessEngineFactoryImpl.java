/*
 * Copyright 2017 Christian Thomas.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package de.chrthms.iot.impl;

import de.chrthms.iot.MicroProcessEngine;
import de.chrthms.iot.MicroProcessEngineFactory;
import de.chrthms.iot.enums.MqttQoS;
import de.chrthms.iot.exceptions.MicroEngineRuntimeException;
import de.chrthms.iot.platform.MicroBpmPlatform;
import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.container.RuntimeContainerDelegate;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.ProcessEngines;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by christian on 18.05.17.
 */
public class MicroProcessEngineFactoryImpl implements MicroProcessEngineFactory {

    private String jdbcDriver = null;
    private String jdbcUrl = null;
    private String jdbcUsername = null;
    private String jdbcPassword = null;

    private String engineName = ProcessEngines.NAME_DEFAULT;

    private boolean isValuePresent(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private boolean isValueEmpty(String value) {
        return !isValuePresent(value);
    }

    @Override
    public MicroProcessEngineFactory jdbcDriver(String driver) {
        this.jdbcDriver = driver;
        return this;
    }

    @Override
    public MicroProcessEngineFactory jdbcUrl(String url) {
        this.jdbcUrl = url;
        return this;

    }

    @Override
    public MicroProcessEngineFactory jdbcUsername(String username) {
        this.jdbcUsername = username;
        return this;

    }

    @Override
    public MicroProcessEngineFactory jdbcPassword(String password) {
        this.jdbcPassword = password;
        return this;

    }

    @Override
    public MicroProcessEngineFactory engineName(String name) {
        this.engineName = name;
        return this;
    }

    @Override
    public MicroProcessEngine build() throws MicroEngineRuntimeException {

        try {

            if (isValueEmpty(jdbcDriver) || isValueEmpty(jdbcUrl)) {
                throw new MicroEngineRuntimeException(new StringBuilder()
                    .append("Missing information to build micro process-engine. At least jdbcDriver = '")
                    .append(jdbcDriver)
                    .append("' and jdbcUrl = '")
                    .append(jdbcUrl)
                    .append("' must be set.")
                    .toString());
            }

            ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration()
                    .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
                    .setJdbcDriver(jdbcDriver)
                    .setJdbcUrl(jdbcUrl)
                    .setJdbcUsername(jdbcUsername)
                    .setJdbcPassword(jdbcPassword)
                    .setHistory(ProcessEngineConfiguration.HISTORY_AUDIT)
                    .setJobExecutorActivate(Boolean.TRUE)
                    .setProcessEngineName(engineName)
                    .buildProcessEngine();

            MicroProcessEngine microProcessEngine = new MicroProcessEngineImpl(processEngine);

            RuntimeContainerDelegate.INSTANCE.get().registerProcessEngine(microProcessEngine);

            return microProcessEngine;

        } catch (MicroEngineRuntimeException e) {
          throw e;
        } catch (Exception e) {
            throw new MicroEngineRuntimeException("Could not build micro process-engine. Check exception details!", e);
        }

    }
}
