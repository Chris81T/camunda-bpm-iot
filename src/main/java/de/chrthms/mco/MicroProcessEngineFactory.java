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

package de.chrthms.mco;

import de.chrthms.mco.enums.MqttQoS;
import de.chrthms.mco.exceptions.McoRuntimeException;
import de.chrthms.mco.impl.MicroProcessEngineFactoryImpl;

/**
 * Created by christian on 18.05.17.
 */
public interface MicroProcessEngineFactory {

    static MicroProcessEngineFactory getInstance() {
        return new MicroProcessEngineFactoryImpl();
    }

    MicroProcessEngineFactory jdbcDriver(String driver);

    MicroProcessEngineFactory jdbcUrl(String url);

    MicroProcessEngineFactory jdbcUsername(String username);

    MicroProcessEngineFactory jdbcPassword(String password);

    /**
     * Must be activated to have mqtt support, else no MQTT client will be activated.
     * @param enabled false by default
     * @return the factory
     */
    MicroProcessEngineFactory mqttEnabled(Boolean enabled);

    /**
     * Describes the broker location. For example:
     * "tcp://broker.mqttdashboard.com" or
     * "tcp://iot.eclipse.org:1883"
     *
     * @param broker must be set for establish a connection.
     * @return the factory
     */
    MicroProcessEngineFactory mqttBroker(String broker);

    MicroProcessEngineFactory mqttUsername(String username);

    MicroProcessEngineFactory mqttPassword(String password);

    /**
     *
     * Set the Quality of Service
     *
     * A good explanation
     * http://www.hivemq.com/blog/mqtt-essentials-part-6-mqtt-quality-of-service-levels
     *
     * @param qos "Exactly once (2)" per default
     * @return the factory
     */
    MicroProcessEngineFactory mqttQoS(MqttQoS qos);

    /**
     * Specifies a client-id.
     * @param clientId per default an id will be generated, if not set.
     * @return the factory
     */
    MicroProcessEngineFactory mqttClientId(String clientId);

    /**
     * A good explanation
     * http://www.hivemq.com/blog/mqtt-essentials-part-8-retained-messages
     * @param retained false per default
     * @return the factory
     */
    MicroProcessEngineFactory mqttRetained(Boolean retained);

    MicroProcessEngine build()  throws McoRuntimeException;

}
