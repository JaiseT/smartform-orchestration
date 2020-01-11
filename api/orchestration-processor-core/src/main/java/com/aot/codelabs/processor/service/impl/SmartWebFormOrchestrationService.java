package com.aot.codelabs.processor.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.aot.codelabs.accelerator.context.IOrchestrationConstants;
import com.aot.codelabs.processor.model.SmartWebFormOrchestration;
import com.aot.codelabs.processor.repository.SmartWebFormOrchestrationRepository;
import com.aot.codelabs.processor.service.ISmartWebFormOrchestrationService;

/**
 * Implementation class for orchestration process of system.
 * 
 * @author Sumathi Thirumani
 */
@Service("smartWebFormOrchestrationService")
public class SmartWebFormOrchestrationService implements ISmartWebFormOrchestrationService {
	
	@Autowired
	private SmartWebFormOrchestrationRepository smartWebFormOrchestrationRepository;

	@Override
	public SmartWebFormOrchestration saveOrchestrationInstance(SmartWebFormOrchestration data) {
		return smartWebFormOrchestrationRepository.save(data);
	}
	
	@Override
	public SmartWebFormOrchestration updateOrchestrationInstance(SmartWebFormOrchestration data) {
		smartWebFormOrchestrationRepository.updateProcessInstanceID(data.getProcessInstanceId(), "NEW",
				data.getProcessOwnerGroup(),data.getProcessAttribs(),data.getSystemCorrId());
		return smartWebFormOrchestrationRepository.findById(data.getSystemCorrId()).get();
		//return data;
	}
	
	@Override
	public SmartWebFormOrchestration updateOrchestrationInstanceStatus(SmartWebFormOrchestration data) {
		smartWebFormOrchestrationRepository.updateProcessInstanceStatus(data.getStatus(),
				data.getSystemCorrId());
		return smartWebFormOrchestrationRepository.findById(data.getSystemCorrId()).get();
	}

	@Override
	public SmartWebFormOrchestration getOrchestrationInstance(Integer systemCorrId) {
		return smartWebFormOrchestrationRepository.findById(systemCorrId).get();
	}
	
	@Override
	public SmartWebFormOrchestration getOrchestrationInstanceByProcessId(String processInstanceId) {
		return smartWebFormOrchestrationRepository.findByProcessInstanceId(processInstanceId);
	}

	@Override
	public List<SmartWebFormOrchestration> getAllUnclaimedInstances() {
		return smartWebFormOrchestrationRepository.findByStatusOrderByCreatedTs(IOrchestrationConstants.OrchestrationState.NEW.name());
	}

	@Override
	public List<SmartWebFormOrchestration> getOrchestrationInstances(String userId, String ownerGroup, List<String> status) {
		List<SmartWebFormOrchestration> rspList = new ArrayList<SmartWebFormOrchestration>();
		List<String> criteriaStatus = new ArrayList<String>();
		if(CollectionUtils.isEmpty(status)){
			rspList.addAll(getAllUnclaimedInstances());
			criteriaStatus.add(IOrchestrationConstants.OrchestrationState.IN_PROGRESS.name());
			criteriaStatus.add(IOrchestrationConstants.OrchestrationState.COMPLETED.name());
		} else {
			criteriaStatus.addAll(status);
		}
		if(StringUtils.isNotEmpty(userId)) {
			rspList.addAll(smartWebFormOrchestrationRepository.findByUserIdAndStatusIn(userId, criteriaStatus));
		}
		if(StringUtils.isNotEmpty(ownerGroup)) {
			rspList.addAll(smartWebFormOrchestrationRepository.findByprocessOwnerGroupAndStatusInOrderByCreatedTs(ownerGroup, criteriaStatus));
		}
		return rspList;
	}

	@Override
	public SmartWebFormOrchestration getOrchestrationInstanceByAttribute(String attribValue) {
		return smartWebFormOrchestrationRepository.findByprocessAttribsIsLike(attribValue);
	}

	@Override
	public List<SmartWebFormOrchestration> getAllInstances(String userId) {
		if(StringUtils.isNotEmpty(userId)) {
			return smartWebFormOrchestrationRepository.findByUserIdOrderByCreatedTsDesc(userId);
		}
		return smartWebFormOrchestrationRepository.findAll();
	}

}