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

package de.chrthms.bpm.iot;

import de.chrthms.bpm.iot.services.MicroMqttService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.repository.Deployment;

/**
 * Created by christian on 18.05.17.
 */
public interface MicroProcessEngine extends ProcessEngine {

    MicroMqttService getMicroMqttService();

    /**
     * Simply tell the name of bpmn file.
     *
     * The convention is, that under resources folder a "processes" folder holds all processes
     *
     * @param filename e.g. "my-process.bpmn"
     * @return
     */
    Deployment createProcessDeploymentFromResource(String filename);

    /**
     * Simply tell the name of dmn file.
     *
     * The convention is, that under resources folder a "rules" folder holds all processes
     *
     * @param filename e.g. "my-rule.dmn"
     * @return
     */
    Deployment createRuleDeploymentFromResource(String filename);

    /**
     * Simply tell the name of cmmn file.
     *
     * The convention is, that under resources folder a "cases" folder holds all processes
     *
     * @param filename e.g. "my-case.cmmn"
     * @return
     */
    Deployment createCaseDeploymentFromResource(String filename);

    /**
     * Use this one, if process file is not inside the src/main/resources/processes folder to deploy an available
     * bpmn process.
     *
     * @param folderName e.g. "/myspecial/folder/to/processes"
     * @param filename e.g. "my-process.bpmn"
     * @return
     */
    Deployment createDeploymentFromResource(String folderName, String filename);

}
