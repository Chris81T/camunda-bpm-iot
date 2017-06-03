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

import de.chrthms.bpm.iot.delegates.MicroDelegateExecution;
import de.chrthms.bpm.iot.delegates.MicroDelegateTask;
import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.delegate.DelegateCaseExecution;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.task.IdentityLink;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.UserTask;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by christian on 03.06.17.
 */
public class MicroDelegateTaskImpl extends AbstractMicroEngineServicesImpl implements MicroDelegateTask {

    private final DelegateTask delegateTask;

    public MicroDelegateTaskImpl(DelegateTask delegateTask) {
        this.delegateTask = delegateTask;
    }

    @Override
    public MicroDelegateExecution getMicroExecution() {
        return new MicroDelegateExecutionImpl(getExecution());
    }

    @Override
    public String getId() {
        return delegateTask.getId();
    }

    @Override
    public String getName() {
        return delegateTask.getName();
    }

    @Override
    public void setName(String name) {
        delegateTask.setName(name);
    }

    @Override
    public String getDescription() {
        return delegateTask.getDescription();
    }

    @Override
    public void setDescription(String description) {
        delegateTask.setDescription(description);
    }

    @Override
    public int getPriority() {
        return delegateTask.getPriority();
    }

    @Override
    public void setPriority(int priority) {
        delegateTask.setPriority(priority);
    }

    @Override
    public String getProcessInstanceId() {
        return delegateTask.getProcessInstanceId();
    }

    @Override
    public String getExecutionId() {
        return delegateTask.getExecutionId();
    }

    @Override
    public String getProcessDefinitionId() {
        return delegateTask.getProcessDefinitionId();
    }

    @Override
    public String getCaseInstanceId() {
        return delegateTask.getCaseInstanceId();
    }

    @Override
    public String getCaseExecutionId() {
        return delegateTask.getCaseExecutionId();
    }

    @Override
    public String getCaseDefinitionId() {
        return delegateTask.getCaseDefinitionId();
    }

    @Override
    public Date getCreateTime() {
        return delegateTask.getCreateTime();
    }

    @Override
    public String getTaskDefinitionKey() {
        return delegateTask.getTaskDefinitionKey();
    }

    @Override
    public DelegateExecution getExecution() {
        return delegateTask.getExecution();
    }

    @Override
    public DelegateCaseExecution getCaseExecution() {
        return delegateTask.getCaseExecution();
    }

    @Override
    public String getEventName() {
        return delegateTask.getEventName();
    }

    @Override
    public void addCandidateUser(String userId) {
        delegateTask.addCandidateUser(userId);
    }

    @Override
    public void addCandidateUsers(Collection<String> candidateUsers) {
        delegateTask.addCandidateUsers(candidateUsers);
    }

    @Override
    public void addCandidateGroup(String groupId) {
        delegateTask.addCandidateGroup(groupId);
    }

    @Override
    public void addCandidateGroups(Collection<String> candidateGroups) {
        delegateTask.addCandidateGroups(candidateGroups);
    }

    @Override
    public String getOwner() {
        return delegateTask.getOwner();
    }

    @Override
    public void setOwner(String owner) {
        delegateTask.setOwner(owner);
    }

    @Override
    public String getAssignee() {
        return delegateTask.getAssignee();
    }

    @Override
    public void setAssignee(String assignee) {
        delegateTask.setAssignee(assignee);
    }

    @Override
    public Date getDueDate() {
        return delegateTask.getDueDate();
    }

    @Override
    public void setDueDate(Date dueDate) {
        delegateTask.setDueDate(dueDate);
    }

    @Override
    public String getDeleteReason() {
        return delegateTask.getDeleteReason();
    }

    @Override
    public void addUserIdentityLink(String userId, String identityLinkType) {
        delegateTask.addUserIdentityLink(userId, identityLinkType);
    }

    @Override
    public void addGroupIdentityLink(String groupId, String identityLinkType) {
        delegateTask.addGroupIdentityLink(groupId, identityLinkType);
    }

    @Override
    public void deleteCandidateUser(String userId) {
        delegateTask.deleteCandidateUser(userId);
    }

    @Override
    public void deleteCandidateGroup(String groupId) {
        delegateTask.deleteCandidateGroup(groupId);
    }

    @Override
    public void deleteUserIdentityLink(String userId, String identityLinkType) {
        delegateTask.deleteUserIdentityLink(userId, identityLinkType);
    }

    @Override
    public void deleteGroupIdentityLink(String groupId, String identityLinkType) {
        delegateTask.deleteGroupIdentityLink(groupId, identityLinkType);
    }

    @Override
    public Set<IdentityLink> getCandidates() {
        return delegateTask.getCandidates();
    }

    @Override
    public UserTask getBpmnModelElementInstance() {
        return delegateTask.getBpmnModelElementInstance();
    }

    @Override
    public String getTenantId() {
        return delegateTask.getTenantId();
    }

    @Override
    public void complete() {
        delegateTask.complete();
    }

    @Override
    public String getVariableScopeKey() {
        return delegateTask.getVariableScopeKey();
    }

    @Override
    public Map<String, Object> getVariables() {
        return delegateTask.getVariables();
    }

    @Override
    public VariableMap getVariablesTyped() {
        return delegateTask.getVariablesTyped();
    }

    @Override
    public VariableMap getVariablesTyped(boolean deserializeValues) {
        return delegateTask.getVariablesTyped(deserializeValues);
    }

    @Override
    public Map<String, Object> getVariablesLocal() {
        return delegateTask.getVariablesLocal();
    }

    @Override
    public VariableMap getVariablesLocalTyped() {
        return delegateTask.getVariablesLocalTyped();
    }

    @Override
    public VariableMap getVariablesLocalTyped(boolean deserializeValues) {
        return delegateTask.getVariablesLocalTyped(deserializeValues);
    }

    @Override
    public Object getVariable(String variableName) {
        return delegateTask.getVariable(variableName);
    }

    @Override
    public Object getVariableLocal(String variableName) {
        return delegateTask.getVariableLocal(variableName);
    }

    @Override
    public <T extends TypedValue> T getVariableTyped(String variableName) {
        return delegateTask.getVariableTyped(variableName);
    }

    @Override
    public <T extends TypedValue> T getVariableTyped(String variableName, boolean deserializeValue) {
        return delegateTask.getVariableTyped(variableName, deserializeValue);
    }

    @Override
    public <T extends TypedValue> T getVariableLocalTyped(String variableName) {
        return delegateTask.getVariableLocalTyped(variableName);
    }

    @Override
    public <T extends TypedValue> T getVariableLocalTyped(String variableName, boolean deserializeValue) {
        return delegateTask.getVariableLocalTyped(variableName, deserializeValue);
    }

    @Override
    public Set<String> getVariableNames() {
        return delegateTask.getVariableNames();
    }

    @Override
    public Set<String> getVariableNamesLocal() {
        return delegateTask.getVariableNamesLocal();
    }

    @Override
    public void setVariable(String variableName, Object value) {
        delegateTask.setVariable(variableName, value);
    }

    @Override
    public void setVariableLocal(String variableName, Object value) {
        delegateTask.setVariableLocal(variableName, value);
    }

    @Override
    public void setVariables(Map<String, ?> variables) {
        delegateTask.setVariables(variables);
    }

    @Override
    public void setVariablesLocal(Map<String, ?> variables) {
        delegateTask.setVariablesLocal(variables);
    }

    @Override
    public boolean hasVariables() {
        return delegateTask.hasVariables();
    }

    @Override
    public boolean hasVariablesLocal() {
        return delegateTask.hasVariablesLocal();
    }

    @Override
    public boolean hasVariable(String variableName) {
        return delegateTask.hasVariable(variableName);
    }

    @Override
    public boolean hasVariableLocal(String variableName) {
        return delegateTask.hasVariableLocal(variableName);
    }

    @Override
    public void removeVariable(String variableName) {
        delegateTask.removeVariable(variableName);
    }

    @Override
    public void removeVariableLocal(String variableName) {
        delegateTask.removeVariableLocal(variableName);
    }

    @Override
    public void removeVariables(Collection<String> variableNames) {
        delegateTask.removeVariables(variableNames);
    }

    @Override
    public void removeVariablesLocal(Collection<String> variableNames) {
        delegateTask.removeVariablesLocal(variableNames);
    }

    @Override
    public void removeVariables() {
        delegateTask.removeVariables();
    }

    @Override
    public void removeVariablesLocal() {
        delegateTask.removeVariablesLocal();
    }

    @Override
    public BpmnModelInstance getBpmnModelInstance() {
        return delegateTask.getBpmnModelInstance();
    }

    @Override
    public ProcessEngineServices getProcessEngineServices() {
        return delegateTask.getProcessEngineServices();
    }

}
