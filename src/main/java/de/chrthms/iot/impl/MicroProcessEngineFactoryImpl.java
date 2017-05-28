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
import de.chrthms.iot.exceptions.McoRuntimeException;
import de.chrthms.iot.platform.MicroBpmPlatform;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

/**
 * Created by christian on 18.05.17.
 */
public class MicroProcessEngineFactoryImpl implements MicroProcessEngineFactory {

    private String jdbcDriver = null;
    private String jdbcUrl = null;
    private String jdbcUsername = null;
    private String jdbcPassword = null;

    private boolean mqttEnabled = false;
    private String mqttBroker = null;
    private boolean mqttAutoReconnect = false;
    private String mqttUsername = null;
    private String mqttPassword = null;
    private String mqttClientId = null;
    private MqttQoS mqttQoS = MqttQoS.EXACTLY_ONCE;
    private boolean mqttRetained = false;

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
    public MicroProcessEngineFactory mqttEnabled(Boolean enabled) {
        this.mqttEnabled = enabled;
        return this;
    }

    @Override
    public MicroProcessEngineFactory mqttBroker(String broker) {
        this.mqttBroker = broker;
        return this;
    }

    @Override
    public MicroProcessEngineFactory mqttAutoReconnect(Boolean reconnect) {
        this.mqttAutoReconnect = reconnect;
        return this;
    }

    @Override
    public MicroProcessEngineFactory mqttUsername(String username) {
        this.mqttUsername = username;
        return this;
    }

    @Override
    public MicroProcessEngineFactory mqttPassword(String password) {
        this.mqttPassword = password;
        return this;
    }

    @Override
    public MicroProcessEngineFactory mqttQoS(MqttQoS qos) {
        this.mqttQoS = qos;
        return this;
    }

    @Override
    public MicroProcessEngineFactory mqttClientId(String clientId) {
        return this;
    }

    @Override
    public MicroProcessEngineFactory mqttRetained(Boolean retained) {
        return this;
    }

    private MqttAsyncClient buildMqttClient() throws McoRuntimeException {
        try {
            MqttAsyncClient client = new MqttAsyncClient(mqttBroker, mqttClientId != null ? mqttClientId : MqttClient.generateClientId());

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(mqttAutoReconnect);

            if (mqttUsername != null) {
                options.setUserName(mqttUsername);
                options.setPassword(mqttPassword.toCharArray());
            }

            client.connect(options);
            return client;

        } catch (Exception e) {
            throw new McoRuntimeException("Could not build mqtt-client for micro process-engine. Check exception details!", e);
        }
    }

    @Override
    public MicroProcessEngine build() throws McoRuntimeException {

        try {

            MicroProcessEngine microProcessEngine = null;

            if (isValueEmpty(jdbcDriver) || isValueEmpty(jdbcUrl)) {
                throw new McoRuntimeException(new StringBuilder()
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
                    .buildProcessEngine();

            if (mqttEnabled) {
                microProcessEngine = new MicroProcessEngineImpl(processEngine, buildMqttClient(), mqttQoS, mqttRetained);
            } else {
                microProcessEngine = new MicroProcessEngineImpl(processEngine);
            }

            MicroBpmPlatform.setMicroProcessEngine(microProcessEngine);
            return microProcessEngine;

        } catch (McoRuntimeException e) {
          throw e;
        } catch (Exception e) {
            throw new McoRuntimeException("Could not build micro process-engine. Check exception details!", e);
        }

    }
}
