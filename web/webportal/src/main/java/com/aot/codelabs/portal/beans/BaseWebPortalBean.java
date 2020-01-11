package com.aot.codelabs.portal.beans;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.aot.codelabs.processor.model.FormSet;
import com.aot.codelabs.processor.model.SmartWebFormProcess;
import com.aot.codelabs.processor.model.WebForm;
import com.aot.codelabs.processor.model.WorkProcess;


public class BaseWebPortalBean {
	
	@Autowired
    private RestTemplate restTemplate;
	
	@Value("${accelerator.service.URI}")
	private String serviceURI;
	
	@Value("${form.service.URI}")
	private String formServiceURI;
	
	private Map<String,List<String>> msgList;
	
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String getServiceURI() {
		return serviceURI;
	}

	public void setServiceURI(String serviceURI) {
		this.serviceURI = serviceURI;
	}
	
	public String getFormServiceURI() {
		return formServiceURI;
	}

	public void setFormServiceURI(String formServiceURI) {
		this.formServiceURI = formServiceURI;
	}
	
	public Map<String,List<String>> getMsgList() {
		return msgList;
	}

	public void setMsgList(Map<String,List<String>> msgList) {
		this.msgList = msgList;
	}

	public Object getInstanceForClient() {
		return null;
	}
	
	protected FormSet getGroupConfiguration(Integer formsetId) {
		FormSet wgroup = getRestTemplate().getForObject(getServiceURI()+"/admin/formset/" + formsetId, FormSet.class);
		if(wgroup == null) {
			throw new RuntimeException("No configuration found for formsetId : "+formsetId);
		}
		return wgroup;
	}
	
	protected WebForm getFormConfiguration(Integer formId) {
		WebForm form = getRestTemplate().getForObject(getServiceURI()+"/admin/form/" + formId, WebForm.class);
		if(form == null) {
			throw new RuntimeException("No configuration found for formsetId : "+formId);
		}
		return form;
	}
	
	protected WorkProcess getProcessConfiguration(Integer processId) {
		WorkProcess wprc = getRestTemplate().getForObject(getServiceURI()+"/admin/process/" + processId, WorkProcess.class);
		if(wprc == null) {
			throw new RuntimeException("No configuration found for processId : "+processId);
		}
		return wprc;
	}
	
	protected SmartWebFormProcess getWebFormProcessMetaData(Integer mapId) {
		SmartWebFormProcess metadata = getRestTemplate().getForObject(getServiceURI()+"/admin/forms/process/"+mapId, SmartWebFormProcess.class);
		return metadata;
	}
	
	public String getUserName() {
		return getRequest().getUserPrincipal().getName();
	}

	public ApplicationRole getRole() {
		return getRequest().isUserInRole("USER") ? ApplicationRole.USER : 
			getRequest().isUserInRole("DL_STAFF") ?  ApplicationRole.DL_STAFF :
				getRequest().isUserInRole("FL_STAFF") ?  ApplicationRole.FL_STAFF : 
					getRequest().isUserInRole("FOI_STAFF") ?  ApplicationRole.FOI_STAFF : 
					ApplicationRole.MANAGER;
	}
	
	
	private HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	enum ApplicationRole {
		USER,
		DL_STAFF,
		FL_STAFF,
		FOI_STAFF,
		MANAGER;
	}
	
	protected String getGroupName(String formName) {
		if("Freedom-of-Information".equals(formName)) {return "FOI_STAFF";}
		else if ("Driving-License".equals(formName)) {return "DL_STAFF";}
		else if ("Freshwater-License".equals(formName)) {return "FL_STAFF";}
		else {return "MANAGER";}
	}
	
	protected void redirectTo(String url) throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(url);
		//FacesContext context = FacesContext.getCurrentInstance();
	   // context.getExternalContext().dispatch(url);
	   // context.responseComplete();
	}
	
}