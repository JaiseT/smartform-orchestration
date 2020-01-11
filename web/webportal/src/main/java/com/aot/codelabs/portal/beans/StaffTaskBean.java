package com.aot.codelabs.portal.beans;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aot.codelabs.portal.FormActivityInRefHolder;
import com.aot.codelabs.processor.model.SmartWebFormOrchestration;
import com.aot.codelabs.processor.model.SmartWebFormProcess;
import com.aot.codelabs.processor.model.WebForm;
import com.aot.codelabs.processor.model.WorkProcess;

@Component
@ManagedBean("staffTask")
@ViewScoped
public class StaffTaskBean extends BaseWebPortalBean {
	
	private static final Logger logger = LoggerFactory.getLogger(StaffTaskBean.class);
	
	private SmartWebFormOrchestration activeTask;
	private String documentURL;
	private String refId;
	
	@Autowired
	private FormActivityInRefHolder formActivityInRefHolder;
	
	public void getTask() {
		setActiveTask(null);
		setDocumentURL(null);
		setRefId(null);
		SmartWebFormOrchestration[] tasks = getRestTemplate().getForObject(getServiceURI()+"/orchestration/data/unclaimed/group/"+getRole().toString(), SmartWebFormOrchestration[].class);
		if(tasks != null && tasks.length > 0) {
			setActiveTask(tasks[0]);
			SmartWebFormProcess config = getWebFormProcessMetaData(getActiveTask().getMapId());
			Map<String,Object> attribs = new HashMap<String,Object>();
			attribs.put("userid", getUserName());
			String key= formActivityInRefHolder.addToPool(attribs);
			setRefId(key);
			WebForm fmetadata = getFormConfiguration(config.getFormId());
			setDocumentURL(StringUtils.substringBeforeLast(fmetadata.getFormLocation(), "/new")+"/edit/"+getActiveTask().getDataId()+"?"
					+ "pid=O15F&corrId="+getActiveTask().getSystemCorrId()+"&refId="+getRefId());
			
		}else {
			logger.error("No task available for processing.");
		}
	}
	
	public void completeTask() throws IOException {
		try{
		SmartWebFormProcess config = getWebFormProcessMetaData(getActiveTask().getMapId());
		WorkProcess prcInfo = getProcessConfiguration(config.getProcessId());
		String taskId = getValueOf("TASKID",getActiveTask());
		getRestTemplate().postForObject(getServiceURI()+"/orchestration/"+prcInfo.getProcessSource()+"/process/task/"+getUserName()+"/"+taskId+"/complete",Void.class, SmartWebFormOrchestration.class);
		logger.info("Orchestration instance successfully completed for {}", getActiveTask().toString());
		redirectTo("/webportal/staff/success.xhtml");
		} catch(RuntimeException ex) {
		redirectTo("/webportal/staff/failure.xhtml");
		}
	}
	
	
	private String getValueOf(String key, SmartWebFormOrchestration task) {
		String[] attrbs = task.getProcessAttribs().split(",");
		for(String entry : attrbs) {
			if(entry.startsWith(key)) {
				return StringUtils.substringAfter(entry, "=");
			}
		}
		return null;
	}
	
	public SmartWebFormOrchestration getActiveTask() {
		return activeTask;
	}

	public void setActiveTask(SmartWebFormOrchestration activeTask) {
		this.activeTask = activeTask;
	}

	public String getDocumentURL() {
		return documentURL;
	}

	public void setDocumentURL(String documentURL) {
		this.documentURL = documentURL;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

}