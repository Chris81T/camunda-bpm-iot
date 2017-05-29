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

package de.chrthms.iot.platform;

import de.chrthms.iot.MicroProcessEngine;
import de.chrthms.iot.exceptions.MicroEngineRuntimeException;
import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.ProcessEngine;

/**
 * Does the same as the BpmPlatform class but will automatically cast the engine instance to the MicroProcessEngine
 * class
 *
 * Created by christian on 25.05.17.
 */
public final class MicroBpmPlatform {

    public static MicroProcessEngine getMicroProcessEngine() {

        ProcessEngine processEngine = BpmPlatform.getDefaultProcessEngine();

        if (processEngine instanceof MicroProcessEngine) {
            return (MicroProcessEngine) processEngine;
        }

        throw new MicroEngineRuntimeException("Default process engine is not the expected MicroProcessEngine!");
    }

}
