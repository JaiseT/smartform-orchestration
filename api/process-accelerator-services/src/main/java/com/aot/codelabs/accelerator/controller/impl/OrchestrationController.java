package com.aot.codelabs.accelerator.controller.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.aot.codelabs.accelerator.context.OrchestrationClientCategory;
import com.aot.codelabs.accelerator.context.IOrchestrationConstants.OrchestrationState;
import com.aot.codelabs.accelerator.controller.IOrchestrationController;
import com.aot.codelabs.orchestration.ProcessDefinition;
import com.aot.codelabs.orchestration.support.camunda.CamundaTask;
import com.aot.codelabs.processor.model.SmartWebFormOrchestration;
import com.aot.codelabs.processor.service.ISmartWebFormOrchestrationService;
import com.aot.mw.IOrchestrationClient;
import com.aot.mw.IOrchestrationClientFactory;

/**
 * Implementation class for orchestration operations.
 * 
 * @author Sumathi Thirumani
 */
@SuppressWarnings("rawtypes")
@RestController
public class OrchestrationController implements IOrchestrationController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private IOrchestrationClientFactory orchestrationClientFactory;
	
	@Autowired
	private ISmartWebFormOrchestrationService smartWebFormOrchestrationService;
	
	@Override
	public Object createProcessInstance(String pid,String clientId,Map<String,String> addlAttribs) {
		String processInsId = null;
		logger.debug("Creating new process instance for client: {} ; attributs: {} ",clientId, addlAttribs); 
		CamundaTask ctask = new CamundaTask();
		ctask.setProcessDefinitionId(pid);
		ctask.setTenantId("WEBPORTAL");
		CamundaTask resp = (CamundaTask) getOrchestrationClientInstance(clientId).createNewInstance(ctask,addlAttribs);
		processInsId = resp != null && StringUtils.isNoneEmpty(resp.getProcessInstanceId()) 
				?  resp.getProcessInstanceId() : null;
		if(StringUtils.isEmpty(processInsId)) {
			logger.error("Failed to create process instance id for client: {} ; attributs: {} ",clientId, addlAttribs); 
		}
		logger.info("process instance id created successfully: {} ",processInsId);
		return resp;
	}
	

	@Override
	public List<Object> getTaskForGroup(String clientId, String userId, List<String> groups) {
		logger.debug("Fetch task for client: {} ; userId: {}; groups: {} ",clientId, userId, groups); 
		return getOrchestrationClientInstance(clientId).getTaskForUserGroup(userId, groups);
	}


	@Override
	public Object viewTask(String clientId, String taskId) {
		logger.debug("Fetch task for client: {} ; taskId: {}",clientId, taskId);
		return getOrchestrationClientInstance(clientId).getTask(taskId);
	}


	@Override
	public void completeTask(String clientId, String userId, String taskId) {
		logger.debug("Complete task for client: {} ; taskId: {} ",clientId, taskId); 
		getOrchestrationClientInstance(clientId).completeTask(taskId, userId);
		//Update Status
		/*SmartWebFormOrchestration orchObj = smartWebFormOrchestrationService.getOrchestrationInstanceByAttribute(taskId);
		orchObj.setStatus(OrchestrationState.COMPLETED.name());
		smartWebFormOrchestrationService.updateOrchestrationInstanceStatus(orchObj);*/
		logger.info("Complete task for client is succcessful : {} ; taskId: {} ",clientId, taskId); 
	}


	@Override
	public void claimTask(String clientId, String userId, String taskId) {
		logger.debug("Claim task for client: {} ; taskId: {} ",clientId, taskId); 
		getOrchestrationClientInstance(clientId).claimTask(taskId, userId);
		//Update Status
		SmartWebFormOrchestration orchObj = smartWebFormOrchestrationService.getOrchestrationInstanceByAttribute(taskId);
		orchObj.setStatus(OrchestrationState.IN_PROGRESS.name());
		smartWebFormOrchestrationService.updateOrchestrationInstanceStatus(orchObj);
		logger.info("Claim task for client is succcessful : {} ; taskId: {} ",clientId, taskId); 
	}
	
	@Override
	public List<ProcessDefinition> getAllProcessDefinitions(String clientId) {
		logger.debug("Get process definition for client: {} ",clientId); 
		return getOrchestrationClientInstance(clientId).getAllProcesses();
	}

	
	
	private IOrchestrationClient getOrchestrationClientInstance(String clientId){
		if(!OrchestrationClientCategory.contains(clientId.toUpperCase())) {
			throw new RuntimeException("Invalid client ID");
		}
		return orchestrationClientFactory.getOrchestrationClient(OrchestrationClientCategory.valueOf(clientId.toUpperCase()).getValue());
	}


	
}