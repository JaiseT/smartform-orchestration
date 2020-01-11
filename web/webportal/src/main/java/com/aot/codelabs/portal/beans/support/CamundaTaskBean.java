package com.aot.codelabs.portal.beans.support;

import javax.annotation.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.aot.codelabs.orchestration.support.camunda.CamundaTask;
import com.aot.codelabs.portal.beans.BaseWebPortalBean;

@Component
@ManagedBean("camundaTaskBean")
@ViewScoped
public class CamundaTaskBean  extends BaseWebPortalBean {
	
	private static final Logger logger = LoggerFactory.getLogger(CamundaTaskBean.class);
	
	private CamundaTask task;
	
	public void getData(String taskId){
		logger.debug("Fetch data for component taskId {}",taskId);
		fetchTaskForId(taskId);
	}
	
	private void fetchTaskForId(String taskId) {
		logger.debug("Invoke service for taskId {}",taskId);
		if(StringUtils.isNotEmpty(taskId)) {
			CamundaTask task = getRestTemplate().getForObject(getServiceURI()+"/orchestration/camunda/process/task/"+taskId, CamundaTask.class);
			setTask(task);
		}
		logger.debug("task information {}",getTask().toString());
	}
	
	public CamundaTask getTask() {
		return task;
	}

	public void setTask(CamundaTask task) {
		this.task = task;
	}

}