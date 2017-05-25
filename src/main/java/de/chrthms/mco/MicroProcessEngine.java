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

import de.chrthms.mco.exceptions.McoRuntimeException;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.repository.Deployment;

/**
 * Created by christian on 18.05.17.
 */
public interface MicroProcessEngine extends ProcessEngine {

    MicroOpenhabService getMicroOpenhabService();

    /**
     * Simply tell the name of bpmn file.
     *
     * @param filename e.g. "my-process.bpmn"
     * @return
     */
    Deployment createDeploymentFromResource(String filename);

    /**
     * Use this one, if process file is not inside the src/main/resources/processes folder to deploy an available
     * bpmn process.
     *
     * @param folderName e.g. "/myspecial/folder/to/processes"
     * @param filename e.g. "my-process.bpmn"
     * @return
     */
    Deployment createDeploymentFromResource(String folderName, String filename);

    /**
     * TODO First test implementation...
     * @param topic
     * @param message
     */
    void sendMessageToItem(String topic, String message) throws McoRuntimeException;

}
