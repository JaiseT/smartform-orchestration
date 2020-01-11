package com.aot.codelabs.portal.beans;

import java.io.Serializable;

import javax.annotation.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.aot.codelabs.processor.model.SmartWebFormOrchestration;
import com.aot.codelabs.processor.model.SmartWebFormProcess;
@Component
@ManagedBean("successTaskBean")
@ViewScoped
public class SuccessTaskBean extends BaseWebPortalBean  implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(SuccessTaskBean.class);
	
	private Integer corrId;
	
	private SmartWebFormOrchestration task;
	
	public void loadDetails(Integer corrId) {
		SmartWebFormOrchestration insObj = getRestTemplate().getForObject(getServiceURI()+"/orchestration/data/"+corrId, SmartWebFormOrchestration.class);
		if(insObj != null ) {
			setTask(insObj);
		}
	}
	
	public String getFormName(Integer mapId) {
		SmartWebFormProcess config = getWebFormProcessMetaData(mapId);
		return config.getFormName();
	}
	

	public Integer getCorrId() {
		return corrId;
	}

	public void setCorrId(Integer corrId) {
		this.corrId = corrId;
	}

	public SmartWebFormOrchestration getTask() {
		return task;
	}


	public void setTask(SmartWebFormOrchestration task) {
		this.task = task;
	}
}