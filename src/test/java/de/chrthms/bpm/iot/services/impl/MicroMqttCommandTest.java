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
 *
 */

package de.chrthms.bpm.iot.services.impl;

import de.chrthms.bpm.iot.MicroProcessEngine;
import de.chrthms.bpm.iot.MicroProcessEngineFactory;
import de.chrthms.bpm.iot.services.MicroMqttService;
import io.moquette.interception.AbstractInterceptHandler;
import io.moquette.interception.messages.InterceptPublishMessage;
import io.moquette.server.Server;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.assertions.ProcessEngineTests;
import org.eclipse.paho.client.mqttv3.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by christian on 16.06.17.
 */
public class MicroMqttCommandTest {

    private static final String BROKER_URL = "tcp://0.0.0.0:1883";

    private final String driver = "org.h2.Driver";
    private final String url = "jdbc:h2:mem:test";
    private final String username = "sa";
    private final String password = "sa";

    static class PublisherListener extends AbstractInterceptHandler {
        @Override
        public void onPublish(InterceptPublishMessage message) {
            System.out.println("moquette mqtt broker message intercepted, topic: " + message.getTopicName()
                    + ", content: " + new String(message.getPayload().array()));
        }
    }

    private Server mqttBroker = null;
    private MicroProcessEngine engine = null;
    private MicroMqttService mqttService = null;

    @Before
    public void before() throws IOException, URISyntaxException {
        File config = new File(getClass().getResource("/moquette.conf").toURI());
        mqttBroker = new Server();
        mqttBroker.startServer(config);
        mqttBroker.addInterceptHandler(new PublisherListener());

        engine = MicroProcessEngineFactory.getInstance()
                .jdbcPassword(password)
                .jdbcUsername(username)
                .jdbcUrl(url)
                .jdbcDriver(driver)
                .build();

        mqttService = engine.getMicroMqttService();
        mqttService.defaultBroker(BROKER_URL);
    }

    /**
     * Tiny proof of concept ;-)
     *
     * @throws MqttException
     */
    @Test
    public void testServerPublishForFurtherTests() throws MqttException {

        final String topic = "junittesttopic";

        System.out.println("testServerPublishForFurtherTests");

        // how to use eclipse paho to observe a topic
        MqttClient client = new MqttClient(BROKER_URL, MqttClient.generateClientId());

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("connectionLost");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                System.out.println("messageArrived - topic = " + topic + ", message = " + new String(message.getPayload()));

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("deliveryComplete - token = " + token);

            }
        });

        client.connect();
        client.subscribe(topic);

        // using the micro-mqtt mqttService to publish a message to assert, that paho observable will react on it
        mqttService.createMqttCommand()
            .topic(topic)
            .publish("Hello Moquette JUnit World!")
            .execute();

        client.disconnect();
        client.close();
    }

    @Test
    public void subscribeTest() {

        final String topic = "junit/topic";

        Deployment deployment = engine.createProcessDeploymentFromResource("mqtt-send-message-to-process.bpmn");
        ProcessInstance processInstance = engine.getRuntimeService().startProcessInstanceByKey("Process_MqttSendMsqToProcess");

        ProcessEngineTests.assertThat(processInstance)
                .isStarted()
                .isWaitingAt("Task_Receive")
                .isWaitingFor("Message_mqtt");

        mqttService.createMqttCommand()
                .topic(topic)
                .subscribe("Process_MqttSendMsqToProcess", "Message_mqtt", "mqttMessage")
                .execute();

        // using the micro-mqtt mqttService to publish a message to assert, that paho observable will react on it
        mqttService.createMqttCommand()
                .topic(topic)
                .publish("This content should be send over Mqtt Broker back to the mqttService to send it to the running process...")
                .execute();

        ProcessEngineTests.assertThat(processInstance)
                .hasPassed("Task_Receive")
                .isEnded();

    }

}
