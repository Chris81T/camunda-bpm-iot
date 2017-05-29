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
import de.chrthms.iot.enums.MqttQoS;
import de.chrthms.iot.exceptions.MicroEngineRuntimeException;
import de.chrthms.iot.services.MicroMqttService;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.repository.Deployment;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Optional;

/**
 * Created by christian on 18.05.17.
 */
public class MicroProcessEngineImpl implements MicroProcessEngine {

    private static final String DEFAULT_PROCESSES_FOLDER = "/processes";

    private final ProcessEngine processEngine;

    private MqttAsyncClient mqttClient = null;
    private MqttQoS mqttQoS = null;
    private Boolean mqttRetained = null;

    public MicroProcessEngineImpl(ProcessEngine processEngine) {
        this.processEngine = processEngine;
    }

    public MicroProcessEngineImpl(ProcessEngine processEngine, MqttAsyncClient mqttClient, MqttQoS mqttQoS,
                                  boolean mqttRetained) {
        this.processEngine = processEngine;
        this.mqttClient = mqttClient;
        this.mqttQoS = mqttQoS;
        this.mqttRetained = mqttRetained;
    }

    @Override
    public MicroMqttService getMicroMqttService() {

        /**
         * Lazy Loading
         */

        return null;
    }

    @Override
    public Deployment createDeploymentFromResource(String filename) {
        return createDeploymentFromResource(DEFAULT_PROCESSES_FOLDER, filename);
    }

    @Override
    public Deployment createDeploymentFromResource(String folderName, String filename) {
        return getRepositoryService()
                .createDeployment()
                .addInputStream(filename, getClass().getResourceAsStream(new StringBuilder()
                    .append(folderName)
                    .append("/")
                    .append(filename)
                    .toString()))
                .deploy();
    }

    @Override
    public void sendMessageToItem(String topic, String message) throws MicroEngineRuntimeException {
        if (Optional.ofNullable(mqttClient).isPresent()) {
            try {
                mqttClient.publish(topic, message.getBytes(), mqttQoS.getValue(), mqttRetained);
            } catch (MqttException e) {
                throw new MicroEngineRuntimeException("Sending message to item failed!", e);
            }
        }

        throw new MicroEngineRuntimeException("No active MQTT client! Please configure one!");
    }

    @Override
    public String getName() {
        return processEngine.getName();
    }

    @Override
    public void close() {
        processEngine.close();

        if (Optional.ofNullable(mqttClient).isPresent()) {
            try {
                mqttClient.disconnect();
                mqttClient.close();
            } catch (MqttException e) {
                throw new MicroEngineRuntimeException("Disconnecting and closing the MQTT Client failed!", e);
            }
        }
    }

    @Override
    public ProcessEngineConfiguration getProcessEngineConfiguration() {
        return processEngine.getProcessEngineConfiguration();
    }

    @Override
    public RuntimeService getRuntimeService() {
        return processEngine.getRuntimeService();
    }

    @Override
    public RepositoryService getRepositoryService() {
        return processEngine.getRepositoryService();
    }

    @Override
    public FormService getFormService() {
        return processEngine.getFormService();
    }

    @Override
    public TaskService getTaskService() {
        return processEngine.getTaskService();
    }

    @Override
    public HistoryService getHistoryService() {
        return processEngine.getHistoryService();
    }

    @Override
    public IdentityService getIdentityService() {
        return processEngine.getIdentityService();
    }

    @Override
    public ManagementService getManagementService() {
        return processEngine.getManagementService();
    }

    @Override
    public AuthorizationService getAuthorizationService() {
        return processEngine.getAuthorizationService();
    }

    @Override
    public CaseService getCaseService() {
        return processEngine.getCaseService();
    }

    @Override
    public FilterService getFilterService() {
        return processEngine.getFilterService();
    }

    @Override
    public ExternalTaskService getExternalTaskService() {
        return processEngine.getExternalTaskService();
    }

    @Override
    public DecisionService getDecisionService() {
        return processEngine.getDecisionService();
    }

}
