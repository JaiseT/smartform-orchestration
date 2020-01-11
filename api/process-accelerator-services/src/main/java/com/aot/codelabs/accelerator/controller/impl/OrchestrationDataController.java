package com.aot.codelabs.accelerator.controller.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.aot.codelabs.accelerator.controller.IOrchestrationDataController;
import com.aot.codelabs.processor.model.SmartWebFormOrchestration;
import com.aot.codelabs.processor.service.impl.SmartWebFormOrchestrationService;

/**
 * Implementation class for data operations.
 * 
 * @author Sumathi Thirumani
 */
@RestController
public class OrchestrationDataController implements IOrchestrationDataController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrchestrationDataController.class);
	
	@Autowired
	private SmartWebFormOrchestrationService smartWebFormOrchestrationService;

	@Override
	public SmartWebFormOrchestration saveOrchestrationInstance(SmartWebFormOrchestration data) {
		logger.debug("Persist orchestration instance : {} ", data.toString());
		return smartWebFormOrchestrationService.saveOrchestrationInstance(data);
	}
	
	/*@Override
	public SmartWebFormOrchestration updateOrchestrationInstance(SmartWebFormOrchestration data) {
		logger.debug("Update orchestration instance : {} ", data.toString());
		return smartWebFormOrchestrationService.saveOrchestrationInstance(data);
	}*/

	@Override
	public SmartWebFormOrchestration updateOrchestrationInstance(SmartWebFormOrchestration data) {
		logger.debug("update orchestration instance : {} ", data.toString());
		return smartWebFormOrchestrationService.updateOrchestrationInstance(data);
	}

	@Override
	public void updateOrchestrationStatus(Integer systemCorrId, String status) {
		logger.debug("Update orchestration status systemCorrId : {} ; status : {} ", systemCorrId, status);
		SmartWebFormOrchestration insObj = smartWebFormOrchestrationService.getOrchestrationInstance(systemCorrId);
		if(insObj == null) {
			throw new RuntimeException("No data found for systemCorrId : "+systemCorrId);
		}
		insObj.setStatus(status);
		smartWebFormOrchestrationService.updateOrchestrationInstanceStatus(insObj);
		logger.debug("Orchestration status updated for systemCorrId : {} ; status : {} ", systemCorrId, status);
	}

	@Override
	public SmartWebFormOrchestration getOrchestrationInstance(Integer systemCorrId) {
		logger.debug("Retrieve orchestration systemCorrId : {} ", systemCorrId);
		return smartWebFormOrchestrationService.getOrchestrationInstance(systemCorrId);
	}


	@Override
	public List<SmartWebFormOrchestration> getAllUnclaimedInstances() {
		logger.debug("Retrieve all unclaimed instances.");
		List<SmartWebFormOrchestration> avList = new ArrayList<SmartWebFormOrchestration>();
		avList.addAll(smartWebFormOrchestrationService.getAllUnclaimedInstances());
		return avList; 
	}


	@Override
	public List<SmartWebFormOrchestration> getInstancesByUser(String userId) {
		logger.debug("Retrieve all user instances.");
		return smartWebFormOrchestrationService.getAllInstances(userId);
	}


	@Override
	public List<SmartWebFormOrchestration> getInstancesByProcessOwnerGroup(String processOwnerGroup) {
		logger.debug("Retrieve all instances for {} & {}.", processOwnerGroup);
		List<SmartWebFormOrchestration> ownList = new ArrayList<SmartWebFormOrchestration>();
		ownList.addAll(smartWebFormOrchestrationService.getOrchestrationInstances(null, processOwnerGroup, null));
		return ownList;
	}

	@Override
	public List<SmartWebFormOrchestration> getAllInstances() {
		return smartWebFormOrchestrationService.getAllInstances(null);
	}

	@Override
	public List<SmartWebFormOrchestration> getAllUnclaimedInstancesForGroup(String processOwnerGroup) {
		List<String> statusCriteria = new ArrayList<String>();
		statusCriteria.add("NEW");
		return smartWebFormOrchestrationService.getOrchestrationInstances(null,processOwnerGroup,statusCriteria);
	}

	}