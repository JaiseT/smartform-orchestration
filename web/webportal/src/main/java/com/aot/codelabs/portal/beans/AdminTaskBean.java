package com.aot.codelabs.portal.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aot.codelabs.accelerator.context.IOrchestrationConstants;
import com.aot.codelabs.portal.FormActivityInRefHolder;
import com.aot.codelabs.portal.beans.support.BaseTaskBean;
import com.aot.codelabs.processor.model.SmartWebFormOrchestration;
import com.aot.codelabs.processor.model.SmartWebFormProcess;
import com.aot.codelabs.processor.model.WebForm;
import com.aot.codelabs.processor.model.WorkProcess;

@Component
@ManagedBean("adminTask")
@ViewScoped
public class AdminTaskBean extends BaseTaskBean implements Serializable   {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(AdminTaskBean.class);
	
	@Autowired
	private FormActivityInRefHolder formActivityInRefHolder;
	
	public void getTasks() {
		fetchTaskForURI("/orchestration/data/all");
		prepareFilteredList(getTaskList());
	}
	
	public void claimTask(SmartWebFormOrchestration task){
		SmartWebFormProcess config = getWebFormProcessMetaData(task.getMapId());
		WorkProcess prcInfo = getProcessConfiguration(config.getProcessId());
		String taskId = getValueOf("TASKID",task);
		getRestTemplate().postForObject(getServiceURI()+"/orchestration/"+prcInfo.getProcessSource()+"/process/task/"+getUserName()+"/"+taskId+"/claim",Void.class, SmartWebFormOrchestration.class);
		logger.info("Orchestration instance successfully claimed for {}", task.toString());
	}
	
	public void completeTask(SmartWebFormOrchestration task) throws IOException {
		try{
		SmartWebFormProcess config = getWebFormProcessMetaData(task.getMapId());
		WorkProcess prcInfo = getProcessConfiguration(config.getProcessId());
		String taskId = getValueOf("TASKID",task);
		getRestTemplate().postForObject(getServiceURI()+"/orchestration/"+prcInfo.getProcessSource()+"/process/task/"+getUserName()+"/"+taskId+"/complete",Void.class, SmartWebFormOrchestration.class);
		logger.info("Orchestration instance successfully completed for {}", task.toString());
			redirectTo("/webportal/admin/tasks.xhtml");
		} catch(RuntimeException ex) {
			redirectTo("/webportal/admin/failure.xhtml");
		}
	}
	
	public void ClaimAndCompleteTask(SmartWebFormOrchestration task) throws IOException {
		claimTask(task);
		completeTask(task);
	}
	
	public String getDocumentURL(SmartWebFormOrchestration task){
		SmartWebFormProcess config = getWebFormProcessMetaData(task.getMapId());
		Map<String,Object> attribs = new HashMap<String,Object>();
		attribs.put("userid", getUserName());
		String key= formActivityInRefHolder.addToPool(attribs);
		if(IOrchestrationConstants.OrchestrationState.NEW.toString().equals(task.getStatus())) {
			WebForm fmetadata = getFormConfiguration(config.getFormId());
			return StringUtils.substringBeforeLast(fmetadata.getFormLocation(), "/new")+"/edit/"+task.getDataId()+"?"
		+ "pid=O15F&corrId="+task.getSystemCorrId()+"&refId="+key;
		}
		return getFormServiceURI()+"/"+config.getFormsetName().toLowerCase()+"/"+config.getFormName()+"/view/"+task.getDataId();
		
	}
	
	private String getValueOf(String key, SmartWebFormOrchestration task) {
		if(StringUtils.isEmpty(task.getProcessAttribs())) {return null;}
		String[] attrbs = task.getProcessAttribs().split(",");
		for(String entry : attrbs) {
			if(entry.startsWith(key)) {
				return StringUtils.substringAfter(entry, "=");
			}
		}
		return null;
	}

	

}