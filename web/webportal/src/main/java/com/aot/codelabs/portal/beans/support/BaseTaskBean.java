package com.aot.codelabs.portal.beans.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.aot.codelabs.accelerator.context.IOrchestrationConstants;
import com.aot.codelabs.orchestration.support.camunda.CamundaTask;
import com.aot.codelabs.portal.beans.BaseWebPortalBean;
import com.aot.codelabs.processor.model.SmartWebFormOrchestration;
import com.aot.codelabs.processor.model.SmartWebFormProcess;

@Component
public class BaseTaskBean  extends BaseWebPortalBean {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseTaskBean.class);
	
	private List<SmartWebFormOrchestration> taskList;
	private Map<String,List<SmartWebFormOrchestration>> filteredTaskList;
	
	private Map<String,CamundaTask> camundaTaskList;
	
	public void fetchTaskForURI(String PathURI) {
		setTaskList(new ArrayList<SmartWebFormOrchestration>());
		SmartWebFormOrchestration[] tasks = getRestTemplate().getForObject(getServiceURI()+PathURI, SmartWebFormOrchestration[].class);
		if(tasks != null && tasks.length > 0) {
			setTaskList(Arrays.asList(tasks));
		}
		if(CollectionUtils.isEmpty(taskList)) {
			logger.error("No task available for processing.");
		}
	}
	
	protected void prepareFilteredList(List<SmartWebFormOrchestration> taskList){
		Map<String,List<SmartWebFormOrchestration>> filteredMap = new LinkedHashMap<String,List<SmartWebFormOrchestration>>();
		Map<String,CamundaTask> taskMap = new HashMap<String,CamundaTask>();
		if(CollectionUtils.isEmpty(taskList)) {return;}
		List<SmartWebFormOrchestration> newCol = new ArrayList<SmartWebFormOrchestration>();
		List<SmartWebFormOrchestration> compCol = new ArrayList<SmartWebFormOrchestration>();
		List<SmartWebFormOrchestration> progCol = new ArrayList<SmartWebFormOrchestration>();
		for(SmartWebFormOrchestration entry : taskList) {
			CamundaTask task = getTask(StringUtils.substringAfterLast(entry.getProcessAttribs(), "="));
			task.setGroupName(entry.getProcessOwnerGroup());
			taskMap.put(entry.getDataId(), task);
			if(IOrchestrationConstants.OrchestrationState.NEW.toString().equals(entry.getStatus())) {
				newCol.add(entry);
			} else if(IOrchestrationConstants.OrchestrationState.IN_PROGRESS.toString().equals(entry.getStatus())) {
				progCol.add(entry);
			} else if(IOrchestrationConstants.OrchestrationState.COMPLETED.toString().equals(entry.getStatus())) {
				compCol.add(entry);
			}  else {
				newCol.add(entry);
			}
		}
		filteredMap.put(IOrchestrationConstants.OrchestrationState.NEW.toString(), newCol);
		filteredMap.put(IOrchestrationConstants.OrchestrationState.IN_PROGRESS.toString(), progCol);
		filteredMap.put(IOrchestrationConstants.OrchestrationState.COMPLETED.toString(), compCol);
		setFilteredTaskList(filteredMap);
		setCamundaTaskList(taskMap);
	}
	
	private CamundaTask getTask(String taskId) {
		if(StringUtils.isNotEmpty(taskId)) {
			CamundaTask task = getRestTemplate().getForObject(getServiceURI()+"/orchestration/camunda/process/task/"+taskId, CamundaTask.class);
			return task;
		}
		return null;
	}
	

	public String getFormName(Integer mapId) {
		SmartWebFormProcess config = getWebFormProcessMetaData(mapId);
		return config.getFormName();
	}
	
	public List<SmartWebFormOrchestration> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<SmartWebFormOrchestration> taskList) {
		this.taskList = taskList;
	}
	
	public Map<String, List<SmartWebFormOrchestration>> getFilteredTaskList() {
		return filteredTaskList;
	}

	public void setFilteredTaskList(Map<String, List<SmartWebFormOrchestration>> filteredTaskList) {
		this.filteredTaskList = filteredTaskList;
	}
	
	public Map<String, CamundaTask> getCamundaTaskList() {
		return camundaTaskList;
	}

	public void setCamundaTaskList(Map<String, CamundaTask> camundaTaskList) {
		this.camundaTaskList = camundaTaskList;
	}
	
	
}