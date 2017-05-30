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

package de.chrthms.iot;

import de.chrthms.iot.enums.MqttQoS;
import de.chrthms.iot.exceptions.MicroEngineRuntimeException;
import de.chrthms.iot.impl.MicroProcessEngineFactoryImpl;

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

    /**
     * Will only be recognized, if username is set.
     *
     * @param password
     * @return
     */
    MicroProcessEngineFactory jdbcPassword(String password);

    MicroProcessEngineFactory engineName(String name);

    MicroProcessEngine build()  throws MicroEngineRuntimeException;

}
