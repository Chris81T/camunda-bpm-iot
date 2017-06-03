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

package de.chrthms.bpm.iot.delegates.impl;

import de.chrthms.bpm.iot.MicroProcessEngine;
import de.chrthms.bpm.iot.delegates.MicroEngineServices;
import de.chrthms.bpm.iot.exceptions.MicroEngineRuntimeException;
import de.chrthms.bpm.iot.platform.MicroBpmPlatform;
import de.chrthms.bpm.iot.services.MicroMqttService;
import org.camunda.bpm.engine.ProcessEngines;

import java.util.List;
import java.util.Optional;

/**
 * Created by christian on 03.06.17.
 */
public abstract class AbstractMicroEngineServicesImpl implements MicroEngineServices {

    @Override
    public MicroProcessEngine getMicroProcessEngine() {

        Optional<MicroProcessEngine> engine = MicroBpmPlatform.getMicroProcessEngine(ProcessEngines.NAME_DEFAULT);

        if (engine.isPresent()) return engine.get();

        // no default engine exists. check if one engine with alternative name is present
        List<MicroProcessEngine> engines = MicroBpmPlatform.getMicroProcessEngines();
        if (engines.size() == 1) {
            return engines.get(0);
        }

        throw new MicroEngineRuntimeException("At least no default engine or more than one engine with alternative name " +
                "is set. One of them must be the default engine!");
    }

    @Override
    public MicroMqttService getMicroMqttService() {
        return getMicroProcessEngine().getMicroMqttService();
    }

}
