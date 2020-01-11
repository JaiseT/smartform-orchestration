package com.aot.codelabs.portal.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

//import com.aot.codelabs.orchestration.ProcessDefinition;
import com.aot.codelabs.processor.model.FormSet;
import com.aot.codelabs.processor.model.SmartWebFormProcess;
import com.aot.codelabs.processor.model.WebForm;
import com.aot.codelabs.processor.model.WorkProcess;

@Component
@ManagedBean("orchestrationmanager")
@ViewScoped
public class OrchestrationManagerBean extends BaseWebPortalBean  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(OrchestrationManagerBean.class);
	
	private List<FormSet> formsetList;
	private List<WebForm> formList;
	private List<WorkProcess> processList;
	//private List<ProcessDefinition> processList;
	private List<SmartWebFormProcess> settingsList;
	
	
	private String selectedFormset;
	private String selectedForm;
	private String selectedProcess;
	private String status;
	private String comments;
	private Integer id  = 0;
	
	public void loadConfiguration() {
		fetchFormSet();
		fetchProcess();
		loadActiveSetup();
	}
	
	private void loadActiveSetup() {
		settingsList =  new ArrayList<SmartWebFormProcess>();
		SmartWebFormProcess[] settings = getRestTemplate().getForObject(getServiceURI()+"/admin/forms/process", SmartWebFormProcess[].class);
		setSettingsList(Arrays.asList(settings));
		if(CollectionUtils.isEmpty(settingsList)) {
			logger.error("Error occured in fetching active forms");
		}
	}
	private void fetchFormSet() {
		formsetList =  new ArrayList<FormSet>();
		FormSet[] formsetArr = getRestTemplate().getForObject(getServiceURI()+"/admin/formset", FormSet[].class);
		setFormsetList(Arrays.asList(formsetArr));
		if(CollectionUtils.isEmpty(formsetList)) {
			logger.error("Error occured in fetching form set.");
		}
	}
	
	private void fetchProcess() {
		processList =  new ArrayList<WorkProcess>();
		WorkProcess[] processes = getRestTemplate().getForObject(getServiceURI()+"/admin/process", WorkProcess[].class);
		setProcessList(Arrays.asList(processes));
		if(CollectionUtils.isEmpty(processList)) {
			logger.error("Error occured in fetching processes.");
		}
	}
	/*
	private void fetchProcess() {
	processList =  new ArrayList<ProcessDefinition>();
	ProcessDefinition[] processes = getRestTemplate().getForObject(getServiceURI()+"/orchestration/camunda/process-definition", ProcessDefinition[].class);
	setProcessList(Arrays.asList(processes));
	if(CollectionUtils.isEmpty(processList)) {
		logger.error("Error occured in fetching processes.");
	}
}*/
	
	public void refreshFormOptions(ValueChangeEvent event)  {
		formList =  new ArrayList<WebForm>();
		String inp = (String) event.getNewValue();
		if(StringUtils.isEmpty(inp)) {
			logger.info("Formset value cannot be empty.");
			return;
		}
		for(FormSet wobj : formsetList) {
			if(wobj.getformsetId() == getKey(inp)) {
				if(!CollectionUtils.isEmpty(wobj.getForms())) {
					setFormList(wobj.getForms());
				}
			}
		}
	}
	
	public void reset(){
		setSelectedFormset("NA");
		setSelectedForm("NA");
		setSelectedProcess("NA");
		setStatus("NA");
		setComments(null);
		setId(0);
		setMsgList(new HashMap<String,List<String>>());
	}
	
	public void save() {
		setMsgList(new HashMap<String,List<String>>());
		//Validate 
		List<String> msgs = new ArrayList<String>();
		msgs = validate();
		if(!CollectionUtils.isEmpty(msgs)) { getMsgList().put("FAILURE", msgs); return;}
		
		SmartWebFormProcess insObj = new SmartWebFormProcess();
		 insObj.setFormsetId(getKey(getSelectedFormset()));
		 insObj.setFormsetName(getValue(getSelectedFormset()));
		 insObj.setFormId(getKey(getSelectedForm()));
		 insObj.setFormName(getValue(getSelectedForm()));
		 insObj.setProcessId(getKey(getSelectedProcess()));
		 insObj.setProcessName(getValue(getSelectedProcess()));
		 insObj.setStatus(getStatus());
		 insObj.setComments(getComments());
		 insObj.setCreatedBy(getUserName());
		 insObj.setUpdatedBy(getUserName());
		 if(getId() > 0) { insObj.setMapId(getId());}
		 try{
		 logger.debug("Create Process instance {}", insObj.toString());
		 getRestTemplate().postForObject(getServiceURI()+"/admin/forms/process/save", insObj, Void.class);
		 logger.info("Process instance creation successful for {}", insObj.toString());
		 msgs.add("Data has been submitted successfully.");
		 getMsgList().put("SUCCESS",msgs);
		 } catch(RuntimeException ex) {
			 msgs.add("System Error Occured.");
			 getMsgList().put("FAILURE", msgs);
			// throw new RuntimeException("Exception occured in persisting formset data.");
		 }
	}
	
	public void populate(SmartWebFormProcess process) {
		logger.debug("Populate WorkProcess");
		setSelectedFormset(process.getFormsetId()+"_"+process.getFormsetName());
		setSelectedForm(process.getFormId()+"_"+process.getFormName());
		setSelectedProcess(process.getProcessId()+"_"+process.getProcessName());
		setStatus(process.getStatus());
		setComments(process.getComments());
		setId(process.getMapId());
		setMsgList(new HashMap<String,List<String>>());
	}
	
	private Integer getKey(String inp) {
		return Integer.valueOf(StringUtils.substringBefore(inp, "_"));
	}
	private String getValue(String inp) {
		return StringUtils.substringAfter(inp, "_");
	}
	
	public List<FormSet> getFormsetList() {
		return formsetList;
	}

	public void setFormsetList(List<FormSet> formsetList) {
		this.formsetList = formsetList;
	}

	public List<WebForm> getFormList() {
		return formList;
	}

	public void setFormList(List<WebForm> formList) {
		this.formList = formList;
	}

	public List<WorkProcess> getProcessList() {
		return processList;
	}

	public void setProcessList(List<WorkProcess> processList) {
		this.processList = processList;
	}

	/*public List<ProcessDefinition> getProcessList() {
		return processList;
	}

	public void setProcessList(List<ProcessDefinition> processList) {
		this.processList = processList;
	}
*/
	public List<SmartWebFormProcess> getSettingsList() {
		return settingsList;
	}

	public void setSettingsList(List<SmartWebFormProcess> settingsList) {
		this.settingsList = settingsList;
	}

	public String getSelectedFormset() {
		return selectedFormset;
	}

	public void setSelectedFormset(String selectedFormset) {
		this.selectedFormset = selectedFormset;
	}


	public String getSelectedForm() {
		return selectedForm;
	}

	public void setSelectedForm(String selectedForm) {
		this.selectedForm = selectedForm;
	}

	public String getSelectedProcess() {
		return selectedProcess;
	}

	public void setSelectedProcess(String selectedProcess) {
		this.selectedProcess = selectedProcess;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public List<String> validate() {
		List<String> errMsgs = new ArrayList<String>();
		if(StringUtils.isEmpty(getSelectedFormset()) || "NA".equals(getSelectedFormset())) {errMsgs.add("Formset cannot be Empty.");}
		if(StringUtils.isEmpty(getSelectedForm()) || "NA".equals(getSelectedForm())) {errMsgs.add("Form cannot be Empty.");}
		if(StringUtils.isEmpty(getSelectedProcess()) || "NA".equals(getSelectedProcess())) {errMsgs.add("Process cannot be Empty.");}
		if(StringUtils.isEmpty(getStatus()) || "NA".equals(getStatus())) {errMsgs.add("Status cannot be Empty.");}
		Boolean isExists = isDuplicate();
		if(isExists) {errMsgs.add("Record already exists.");}
		return errMsgs;
	}
	
	private Boolean isDuplicate() {
		if(getId() > 0) {return false;}
		for(SmartWebFormProcess entry : getSettingsList()) {
			if(getSelectedFormset().equals(entry.getFormsetId()+"_"+entry.getFormsetName()) &&
					getSelectedForm().equals(entry.getFormId()+"_"+entry.getFormName()) &&
					getSelectedProcess().equals(entry.getProcessId()+"_"+entry.getProcessName())) {
				return true;
			}
		}
		return false;
	}
	
}