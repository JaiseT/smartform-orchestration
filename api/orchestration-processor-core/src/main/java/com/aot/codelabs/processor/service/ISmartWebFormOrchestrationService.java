package com.aot.codelabs.processor.service;

import java.util.List;

import com.aot.codelabs.processor.model.SmartWebFormOrchestration;

/**
 * Interface for orchestration operations.
 * 
 * @author Sumathi Thirumani
 *
 */
public interface ISmartWebFormOrchestrationService {
	
	SmartWebFormOrchestration saveOrchestrationInstance(SmartWebFormOrchestration data);
	SmartWebFormOrchestration updateOrchestrationInstance(SmartWebFormOrchestration data); // eliminate
	SmartWebFormOrchestration updateOrchestrationInstanceStatus(SmartWebFormOrchestration data);
	SmartWebFormOrchestration getOrchestrationInstance(Integer id);
	List<SmartWebFormOrchestration> getAllUnclaimedInstances();
	List<SmartWebFormOrchestration> getAllInstances(String userId);
	List<SmartWebFormOrchestration> getOrchestrationInstances(String userId,String ownerGroup, List<String> status);
	SmartWebFormOrchestration getOrchestrationInstanceByProcessId(String processInstanceId);
	SmartWebFormOrchestration getOrchestrationInstanceByAttribute(String attribValue);
}