package com.aot.codelabs.portal.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aot.codelabs.accelerator.context.IOrchestrationConstants.OrchestrationState;
import com.aot.codelabs.orchestration.support.camunda.CamundaTask;
import com.aot.codelabs.portal.FormActivityInRefHolder;
import com.aot.codelabs.processor.model.SmartWebFormOrchestration;
import com.aot.codelabs.processor.model.SmartWebFormProcess;
import com.aot.codelabs.processor.model.WebForm;
import com.aot.codelabs.processor.model.WorkProcess;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Managed bean for webform.
 * 
 * @author Sumathi Thirumani
 */
@Component
@ManagedBean("smartformBean")
@ViewScoped
public class SmartFormBean extends BaseWebPortalBean  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(SmartFormBean.class);
	
	private String refId;
	
	private String location;
	
	@Autowired
	private FormActivityInRefHolder formActivityInRefHolder;
	
	public String getFormLocation() {
		SmartWebFormProcess prcObj = (SmartWebFormProcess) formActivityInRefHolder.getFromPool(getRefId()).get("SmartWebFormProcess");
		Integer formId = prcObj.getFormId();
		WebForm wform = getRestTemplate().getForObject(getServiceURI()+"/admin/form/"+formId, WebForm.class);
		setLocation(wform.getFormLocation());
		return wform.getFormLocation();
	}
	
	public void submitForReview(){
		logger.info("Submit for review process refId: {} ", refId);
		SmartWebFormProcess form = (SmartWebFormProcess) formActivityInRefHolder.getFromPool(getRefId()).get("SmartWebFormProcess");
		SmartWebFormOrchestration txnObj = (SmartWebFormOrchestration) formActivityInRefHolder.getFromPool(getRefId()).get("SmartWebFormOrchestration");
		//Retrieve configuration
		WorkProcess wpObj = getProcessConfiguration(form.getProcessId());
		Map<String,String> sysAttribs = new HashMap<String,String>();
		sysAttribs.put("groupName", getGroupName(form.getFormName()));
		sysAttribs.put("systemCorrId", txnObj.getSystemCorrId().toString());
		Object mapObj = (CamundaTask) getRestTemplate().postForObject(getServiceURI()+"/orchestration/"+wpObj.getProcessSource().toLowerCase()+"/process/"+wpObj.getProcessKey()+"/create", sysAttribs, CamundaTask.class);
		ObjectMapper mapper = new ObjectMapper();
		CamundaTask task = mapper.convertValue(mapObj, CamundaTask.class);
		logger.info("Orchestration client ID: {}",task.getProcessInstanceId());
		//Create new process-flow record in application datasource.
		txnObj.setProcessInstanceId(task.getProcessInstanceId());
		txnObj.setStatus(OrchestrationState.NEW.name());
		txnObj.setMapId(form.getMapId());
		txnObj.setProcessAttribs("TASKID="+task.getId());
		txnObj.setProcessOwnerGroup(getGroupName(form.getFormName()));
		txnObj.setUpdatedBy(getUserName());
		logger.info("Create NEW orchestration process instance for {} ", txnObj.toString());
		getRestTemplate().postForObject(getServiceURI()+"/orchestration/data/update", txnObj, SmartWebFormOrchestration.class);
		//Remove the indirect reference from data pool
		formActivityInRefHolder.removeFromPool(refId);
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	

	
}