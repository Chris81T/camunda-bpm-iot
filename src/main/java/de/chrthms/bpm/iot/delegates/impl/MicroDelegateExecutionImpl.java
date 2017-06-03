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

package de.chrthms.bpm.iot.delegates.impl;

import de.chrthms.bpm.iot.MicroProcessEngine;
import de.chrthms.bpm.iot.delegates.MicroDelegateExecution;
import de.chrthms.bpm.iot.exceptions.MicroEngineRuntimeException;
import de.chrthms.bpm.iot.platform.MicroBpmPlatform;
import de.chrthms.bpm.iot.services.MicroMqttService;
import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowElement;

import java.util.*;

/**
 * Created by christian on 25.05.17.
 */
public class MicroDelegateExecutionImpl extends AbstractMicroEngineServicesImpl implements MicroDelegateExecution {

    private final DelegateExecution delegateExecution;

    public MicroDelegateExecutionImpl(DelegateExecution delegateExecution) {
        this.delegateExecution = delegateExecution;
    }

    @Override
    public String getProcessInstanceId() {
        return delegateExecution.getProcessInstanceId();
    }

    @Override
    public String getProcessBusinessKey() {
        return delegateExecution.getProcessBusinessKey();
    }

    @Override
    public String getProcessDefinitionId() {
        return delegateExecution.getProcessDefinitionId();
    }

    @Override
    public String getParentId() {
        return delegateExecution.getParentId();
    }

    @Override
    public String getCurrentActivityId() {
        return delegateExecution.getCurrentActivityId();
    }

    @Override
    public String getCurrentActivityName() {
        return delegateExecution.getCurrentActivityName();
    }

    @Override
    public String getActivityInstanceId() {
        return delegateExecution.getActivityInstanceId();
    }

    @Override
    public String getParentActivityInstanceId() {
        return delegateExecution.getParentActivityInstanceId();
    }

    @Override
    public String getCurrentTransitionId() {
        return delegateExecution.getCurrentTransitionId();
    }

    @Override
    public DelegateExecution getProcessInstance() {
        return delegateExecution.getProcessInstance();
    }

    @Override
    public DelegateExecution getSuperExecution() {
        return delegateExecution.getSuperExecution();
    }

    @Override
    public boolean isCanceled() {
        return delegateExecution.isCanceled();
    }

    @Override
    public String getTenantId() {
        return delegateExecution.getTenantId();
    }

    @Override
    public void setVariable(String variableName, Object value, String activityId) {
        delegateExecution.setVariable(variableName, value, activityId);
    }

    @Override
    public String getId() {
        return delegateExecution.getId();
    }

    @Override
    public String getEventName() {
        return delegateExecution.getEventName();
    }

    @Override
    public String getBusinessKey() {
        return delegateExecution.getBusinessKey();
    }

    @Override
    public String getVariableScopeKey() {
        return delegateExecution.getVariableScopeKey();
    }

    @Override
    public Map<String, Object> getVariables() {
        return delegateExecution.getVariables();
    }

    @Override
    public VariableMap getVariablesTyped() {
        return delegateExecution.getVariablesTyped();
    }

    @Override
    public VariableMap getVariablesTyped(boolean deserializeValues) {
        return delegateExecution.getVariablesTyped(deserializeValues);
    }

    @Override
    public Map<String, Object> getVariablesLocal() {
        return delegateExecution.getVariablesLocal();
    }

    @Override
    public VariableMap getVariablesLocalTyped() {
        return delegateExecution.getVariablesLocalTyped();
    }

    @Override
    public VariableMap getVariablesLocalTyped(boolean deserializeValues) {
        return delegateExecution.getVariablesLocalTyped(deserializeValues);
    }

    @Override
    public Object getVariable(String variableName) {
        return delegateExecution.getVariable(variableName);
    }

    @Override
    public Object getVariableLocal(String variableName) {
        return delegateExecution.getVariableLocal(variableName);
    }

    @Override
    public <T extends TypedValue> T getVariableTyped(String variableName) {
        return delegateExecution.getVariableTyped(variableName);
    }

    @Override
    public <T extends TypedValue> T getVariableTyped(String variableName, boolean deserializeValue) {
        return delegateExecution.getVariableTyped(variableName, deserializeValue);
    }

    @Override
    public <T extends TypedValue> T getVariableLocalTyped(String variableName) {
        return delegateExecution.getVariableLocalTyped(variableName);
    }

    @Override
    public <T extends TypedValue> T getVariableLocalTyped(String variableName, boolean deserializeValue) {
        return delegateExecution.getVariableLocalTyped(variableName, deserializeValue);
    }

    @Override
    public Set<String> getVariableNames() {
        return delegateExecution.getVariableNames();
    }

    @Override
    public Set<String> getVariableNamesLocal() {
        return delegateExecution.getVariableNamesLocal();
    }

    @Override
    public void setVariable(String variableName, Object value) {
        delegateExecution.setVariable(variableName, value);
    }

    @Override
    public void setVariableLocal(String variableName, Object value) {
        delegateExecution.setVariableLocal(variableName, value);
    }

    @Override
    public void setVariables(Map<String, ?> variables) {
        delegateExecution.setVariables(variables);
    }

    @Override
    public void setVariablesLocal(Map<String, ?> variables) {
        delegateExecution.setVariablesLocal(variables);
    }

    @Override
    public boolean hasVariables() {
        return delegateExecution.hasVariables();
    }

    @Override
    public boolean hasVariablesLocal() {
        return delegateExecution.hasVariablesLocal();
    }

    @Override
    public boolean hasVariable(String variableName) {
        return delegateExecution.hasVariable(variableName);
    }

    @Override
    public boolean hasVariableLocal(String variableName) {
        return delegateExecution.hasVariableLocal(variableName);
    }

    @Override
    public void removeVariable(String variableName) {
        delegateExecution.removeVariable(variableName);
    }

    @Override
    public void removeVariableLocal(String variableName) {
        delegateExecution.removeVariableLocal(variableName);
    }

    @Override
    public void removeVariables(Collection<String> variableNames) {
        delegateExecution.removeVariables(variableNames);
    }

    @Override
    public void removeVariablesLocal(Collection<String> variableNames) {
        delegateExecution.removeVariablesLocal(variableNames);
    }

    @Override
    public void removeVariables() {
        delegateExecution.removeVariables();
    }

    @Override
    public void removeVariablesLocal() {
        delegateExecution.removeVariablesLocal();
    }

    @Override
    public BpmnModelInstance getBpmnModelInstance() {
        return delegateExecution.getBpmnModelInstance();
    }

    @Override
    public FlowElement getBpmnModelElementInstance() {
        return delegateExecution.getBpmnModelElementInstance();
    }

    @Override
    public ProcessEngineServices getProcessEngineServices() {
        return delegateExecution.getProcessEngineServices();
    }
}
