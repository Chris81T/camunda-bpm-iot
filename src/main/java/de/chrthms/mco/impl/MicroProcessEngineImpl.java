/*
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

package de.chrthms.mco.impl;

import de.chrthms.mco.MicroOpenhabService;
import de.chrthms.mco.MicroProcessEngine;
import org.camunda.bpm.engine.*;

/**
 * Created by christian on 18.05.17.
 */
public class MicroProcessEngineImpl implements MicroProcessEngine {

    private final ProcessEngine processEngine;

    public MicroProcessEngineImpl(ProcessEngine processEngine) {
        this.processEngine = processEngine;
    }

    @Override
    public MicroOpenhabService getMicroOpenhabService() {
        return null;
    }

    @Override
    public String getName() {
        return processEngine.getName();
    }

    @Override
    public void close() {
        processEngine.close();
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
