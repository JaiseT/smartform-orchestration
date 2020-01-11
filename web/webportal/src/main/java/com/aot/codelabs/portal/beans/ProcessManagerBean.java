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

import com.aot.codelabs.orchestration.ProcessDefinition;
import com.aot.codelabs.processor.model.WorkProcess;

@Component
@ManagedBean("processmanager")
@ViewScoped
public class ProcessManagerBean extends BaseWebPortalBean  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(ProcessManagerBean.class);
	
	private List<WorkProcess> processList;
	private List<ProcessDefinition> processDefnList;
	
	private String source;
	private String processKey;
	private String description;
	private String status;
	private String comments;
	private Integer id  = 0;
	
	public void loadConfiguration() {
		fetchProcess();
	}
	
	
	private void fetchProcess() {
		processList =  new ArrayList<WorkProcess>();
		WorkProcess[] processes = getRestTemplate().getForObject(getServiceURI()+"/admin/process", WorkProcess[].class);
		setProcessList(Arrays.asList(processes));
		if(CollectionUtils.isEmpty(processList)) {
			logger.error("Error occured in fetching processes.");
		}
	}
	
	public void refreshProcessOptions(ValueChangeEvent event) {
		processDefnList =  new ArrayList<ProcessDefinition>();
		String inp = (String) event.getNewValue();
		if(StringUtils.isEmpty(inp)) {
			logger.info("Source value cannot be empty.");
			return;
		}
		ProcessDefinition[] processes = getRestTemplate().getForObject(getServiceURI()+"/orchestration/"+inp.toLowerCase()+"/process-definition", ProcessDefinition[].class);
		setProcessDefnList(Arrays.asList(processes));
		if(CollectionUtils.isEmpty(processDefnList)) {
			logger.error("Error occured in fetching processes Definition.");
		}
	}
	
	public void reset(){
		setSource("NA");
		setProcessKey("NA");
		setDescription(null);
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
		 WorkProcess insObj = new WorkProcess();
		 insObj.setProcessKey(getKey(getProcessKey()));
		 insObj.setProcessName(getValue(getProcessKey()));
		 insObj.setProcessDescription(getDescription());
		 insObj.setProcessSource(getSource());
		 insObj.setStatus(getStatus());
		 insObj.setComments(getComments());
		 insObj.setCreatedBy(getUserName());
		 insObj.setUpdatedBy(getUserName());
		 if(getId() > 0) { insObj.setProcessId(getId());}
		 try {
		 logger.debug("Create Process instance {}", insObj.toString());
		 getRestTemplate().postForObject(getServiceURI()+"/admin/process/save", insObj, Void.class);
		 logger.info("Process instance creation successful for {}", insObj.toString());
		 reset();
		 msgs.add("Data has been submitted successfully.");
		 getMsgList().put("SUCCESS",msgs);
		} catch(RuntimeException ex) {
			msgs.add("System Error Occured.");
			getMsgList().put("FAILURE", msgs);
		// throw new RuntimeException("Exception occured in persisting formset data.");
	 }
	}
	
	public void populate(WorkProcess process) {
		logger.debug("Populate WorkProcess");
		setSource(process.getProcessSource());
		setProcessKey(process.getProcessKey()+"~"+process.getProcessName());
		setDescription(process.getProcessDescription());
		setStatus(process.getStatus());
		setComments(process.getComments());
		setId(process.getProcessId());
		setMsgList(new HashMap<String,List<String>>());
	}
	
	private String getKey(String inp) {
		return StringUtils.substringBefore(inp, "~");
	}
	private String getValue(String inp) {
		return StringUtils.substringAfter(inp, "~");
	}
	

	public List<WorkProcess> getProcessList() {
		return processList;
	}

	public void setProcessList(List<WorkProcess> processList) {
		this.processList = processList;
	}

	

	public List<ProcessDefinition> getProcessDefnList() {
		return processDefnList;
	}

	public void setProcessDefnList(List<ProcessDefinition> processDefnList) {
		this.processDefnList = processDefnList;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProcessKey() {
		return processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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
		if(StringUtils.isEmpty(getSource()) || "NA".equals(getSource())) {errMsgs.add("Source cannot be Empty.");}
		if(StringUtils.isEmpty(getProcessKey()) || "NA".equals(getProcessKey())) {errMsgs.add("Process cannot be Empty.");}
		if(StringUtils.isEmpty(getStatus()) || "NA".equals(getStatus())) {errMsgs.add("Status cannot be Empty.");}
		
		Boolean isExists = isDuplicate();
		if(isExists) {errMsgs.add("Record already exists.");}
		return errMsgs;
	}
	
	private Boolean isDuplicate() {
		if(getId() > 0) {return false;}
		for(WorkProcess entry : getProcessList()) {
			if(getValue(getProcessKey()).equals(entry.getProcessName())) {
				return true;
			}
		}
		return false;
	}
	
}