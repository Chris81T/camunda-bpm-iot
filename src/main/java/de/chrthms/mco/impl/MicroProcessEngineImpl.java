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
