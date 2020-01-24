package com.aot.codelabs.portal.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.aot.codelabs.accelerator.context.IOrchestrationConstants.OrchestrationState;
import com.aot.codelabs.orchestration.support.camunda.CamundaTask;
import com.aot.codelabs.portal.FormActivityInRefHolder;
import com.aot.codelabs.processor.model.SmartWebFormOrchestration;
import com.aot.codelabs.processor.model.SmartWebFormProcess;
import com.aot.codelabs.processor.model.WebForm;
import com.aot.codelabs.processor.model.WorkProcess;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Managed bean for handling user forms.
 * 
 * @author Sumathi Thirumani
 */
@Component
@ManagedBean("userTask")
@ViewScoped
public class UserTaskBean extends BaseWebPortalBean  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(UserTaskBean.class);
	
	
	private List<SmartWebFormProcess> formList;
	private List<String> filterdForms;
	
	private String category;
	private String formName;
	
	private String formURI;
	
	private Boolean submissionState;
	
	@Autowired
	private FormActivityInRefHolder formActivityInRefHolder;
	
	/**
	 * Retrieves all active forms managed in system.
	 */
	public void getAllMappedForms(){
		SmartWebFormProcess[] forms = getRestTemplate().getForObject(getServiceURI()+"/admin/forms/process", SmartWebFormProcess[].class);
		setFormList(Arrays.asList(forms));
		if(CollectionUtils.isEmpty(formList)) {
			logger.error("Error occured in fetching active forms");
		}
	}
	
	public List<String> getFormset() {
		List<String> formsets = new ArrayList<String>();
		for(SmartWebFormProcess entry: getFormList()) {
			if(!formsets.contains(entry.getFormsetName())) {
				formsets.add(entry.getFormsetName());
			}
		}
		return formsets;
	}
	public void getForms(ValueChangeEvent event) {
		System.out.println(event.getNewValue());
		setCategory(event.getNewValue().toString());
		setFilterdForms(null);
		String formsetName = (String) event.getNewValue();
		List<String> forms = new ArrayList<String>();
		for(SmartWebFormProcess entry: getFormList()) {
			if(!forms.contains(entry.getFormName()) && formsetName.equals(entry.getFormsetName())) {
				forms.add(entry.getFormName());
			}
		}
		setFilterdForms(forms);
		System.out.println(getFilterdForms());
	}
	
	public void getDocumentURI(ValueChangeEvent event) {
		System.out.println("-2-"+event.getNewValue());
		setFormName(event.getNewValue().toString());
		if(StringUtils.isEmpty(getCategory())|| StringUtils.isEmpty(getFormName())) {
			logger.error("Invalid selection");
			return;
		}
		SmartWebFormProcess activeForm = getActiveForm();
		Map<String,Object> data= new HashMap<String,Object>();
		data.put("SmartWebFormProcess",  activeForm);
		data.put("userid", getUserName());
		String key = formActivityInRefHolder.addToPool(data);
		logger.debug("ID added to pool"+key+" : url : "+activeForm.getFormId());
		WebForm wform = getRestTemplate().getForObject(getServiceURI()+"/admin/form/"+activeForm.getFormId(), WebForm.class);
		setFormURI(wform.getFormLocation()+"?refId="+key);
	}
	
	/**
	 * This integrates with any portal.
	 * Potential changes of having SSO as pre-render step.
	 * 
	 * @param activeForm
	 * @throws IOException
	 */
	public void openForm(SmartWebFormProcess activeForm) throws IOException {
		Map<String,Object> data= new HashMap<String,Object>();
		data.put("SmartWebFormProcess", activeForm);
		data.put("userid", getUserName());
		String key = formActivityInRefHolder.addToPool(data);
		logger.debug("ID added to pool"+key+" : url : "+activeForm.getFormId());
		FacesContext.getCurrentInstance().getExternalContext().redirect("/webportal/user/smartform.xhtml?refId="+key);
	}
	
	
	public void applyForSelection() throws IOException {
		if(StringUtils.isEmpty(getCategory())|| StringUtils.isEmpty(getFormName())) {
			logger.error("Invalid selection");
			return;
		}
		SmartWebFormProcess activeForm = getActiveForm();
		Map<String,Object> data= new HashMap<String,Object>();
		data.put("SmartWebFormProcess",  activeForm);
		data.put("userid", getUserName());
		String key = formActivityInRefHolder.addToPool(data);
		logger.debug("ID added to pool"+key+" : url : "+activeForm.getFormId());
		WebForm wform = getRestTemplate().getForObject(getServiceURI()+"/admin/form/"+activeForm.getFormId(), WebForm.class);
		setFormURI(wform.getFormLocation()+"?refId="+key);
	}
	

	public void submitForReview(String refkey){
		try{
		String refId = StringUtils.substringAfterLast(refkey, "refId=");
		logger.info("Submit for review process refId: {} {}", refkey, refId);
		SmartWebFormProcess form = (SmartWebFormProcess) formActivityInRefHolder.getFromPool(refId).get("SmartWebFormProcess");
		SmartWebFormOrchestration txnObj = (SmartWebFormOrchestration) formActivityInRefHolder.getFromPool(refId).get("SmartWebFormOrchestration");
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
		//txnObj.setStatus(OrchestrationState.NEW.name()); //FIXME what's causing state change of txnObj?
		txnObj.setStatus("NEW"); //FIXME temporary fix for issue with txnObj getting reinitialized.
		txnObj.setMapId(form.getMapId());
		txnObj.setProcessAttribs("TASKID="+task.getId());
		txnObj.setProcessOwnerGroup(getGroupName(form.getFormName()));
		txnObj.setUpdatedBy(getUserName());
		logger.info("Create NEW orchestration process instance for {} ", txnObj.toString());
		getRestTemplate().postForObject(getServiceURI()+"/orchestration/data/update", txnObj, SmartWebFormOrchestration.class);
		//Remove the indirect reference from data pool
		//formActivityInRefHolder.removeFromPool(refId);
		setSubmissionState(true);
		}
		catch(RuntimeException ex) {
			logger.error("Exception occured in submission",ex);
			setSubmissionState(false);
		}
	}
	
	private SmartWebFormProcess getActiveForm(){
		for(SmartWebFormProcess entry: getFormList()) {
			if(category.equals(entry.getFormsetName()) && formName.equals(entry.getFormName())) {
				return entry;
			}
		}
		return null;
	}
	
	public List<SmartWebFormProcess> getFormList() {
		return formList;
	}

	public void setFormList(List<SmartWebFormProcess> formList) {
		this.formList = formList;
	}

	public List<String> getFilterdForms() {
		return filterdForms;
	}

	public void setFilterdForms(List<String> filterdForms) {
		this.filterdForms = filterdForms;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getFormURI() {
		return formURI;
	}

	public void setFormURI(String formURI) {
		this.formURI = formURI;
	}

	public Boolean getSubmissionState() {
		return submissionState;
	}

	public void setSubmissionState(Boolean submissionState) {
		this.submissionState = submissionState;
	}

}