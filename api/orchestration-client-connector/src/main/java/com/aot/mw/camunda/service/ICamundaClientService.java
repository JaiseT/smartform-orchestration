package com.aot.mw.camunda.service;

import com.aot.codelabs.orchestration.support.camunda.CamundaTask;
import com.aot.mw.IOrchestrationClient;

/**
 * Interface for hooking in camunda specific functional operations.
 * 
 * @author Sumathi Thirumani
 */
public interface ICamundaClientService extends IOrchestrationClient<CamundaTask> {
}