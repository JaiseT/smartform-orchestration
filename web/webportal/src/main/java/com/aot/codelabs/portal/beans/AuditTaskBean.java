package com.aot.codelabs.portal.beans;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.aot.codelabs.portal.beans.support.BaseTaskBean;
import com.aot.codelabs.processor.model.SmartWebFormOrchestration;
import com.aot.codelabs.processor.model.SmartWebFormProcess;
import com.aot.codelabs.processor.model.WebForm;

@Component
@ManagedBean("auditTask")
@ViewScoped
public class AuditTaskBean extends BaseTaskBean  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(AuditTaskBean.class);
	
	
	
	public void loadConfiguration() {
		fetchTaskForURI("/orchestration/data/user/"+getUserName());
		prepareFilteredList(getTaskList());
	}
	
	/**
	 * This integrates with any portal.
	 * Potential changes of having SSO as pre-render step.
	 * 
	 * @param activeForm
	 * @throws IOException
	 */
	public String getFormURI(SmartWebFormOrchestration activetask) throws IOException {
		SmartWebFormProcess config = getWebFormProcessMetaData(activetask.getMapId());
		WebForm fmetadata = getFormConfiguration(config.getFormId());
		return StringUtils.substringBeforeLast(fmetadata.getFormLocation(), "/new")+"/view/"+activetask.getDataId();
	}
	

}