package com.aot.codelabs.portal.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.aot.codelabs.processor.model.FormSet;

@Component
@ManagedBean("formsetmanager")
@ViewScoped
public class FormSetManagerBean extends BaseWebPortalBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(FormSetManagerBean.class);
	
	private List<FormSet> formsetList;
	
	private String name;
	private String description;
	private String status;
	private String comments;
	private Integer id  = 0;
	
	
	public void loadConfiguration() {
		fetchFormSet();
	}
	
	private void fetchFormSet() {
		formsetList = new ArrayList<FormSet>();
		FormSet[] formsetArr = getRestTemplate().getForObject(getServiceURI()+"/admin/formset", FormSet[].class);
		setFormsetList(Arrays.asList(formsetArr));
		if(CollectionUtils.isEmpty(formsetList)) {
			logger.error("Error occured in fetching form set.");
		}
	}
	
	public void reset(){
		setName(null);
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
		
		FormSet insObj = new FormSet();
		 insObj.setformSetName(getName());
		 insObj.setformsetDescription(getDescription());
		 insObj.setStatus(getStatus());
		 insObj.setComments(getComments());
		 insObj.setCreatedBy(getUserName());
		 insObj.setUpdatedBy(getUserName());
		 if(getId() > 0) { insObj.setformsetId(getId());}
		 try {
		 logger.debug("Create Process instance {}", insObj.toString());
		 getRestTemplate().postForObject(getServiceURI()+"/admin/formset/save", insObj, Void.class);
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
	
	public void populate(FormSet formset) {
		logger.debug("Populate workgroup");
		setName(formset.getformsetName());
		setDescription(formset.getformsetDescription());
		setStatus(formset.getStatus());
		setComments(formset.getComments());
		setId(formset.getformsetId());
		setMsgList(new HashMap<String,List<String>>());
	}

	public List<FormSet> getFormsetList() {
		return formsetList;
	}

	public void setFormsetList(List<FormSet> formsetList) {
		this.formsetList = formsetList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		if(StringUtils.isEmpty(getName())) {errMsgs.add("Name cannot be Empty.");}
		if(StringUtils.isEmpty(getStatus()) || "NA".equals(getStatus())) {errMsgs.add("Status cannot be Empty.");}
		Boolean isExists = isDuplicate();
		if(isExists && StringUtils.isNotEmpty(getName())) {errMsgs.add("Record already exists.");}
		return errMsgs;
	}
	
	private Boolean isDuplicate() {
		if(getId() > 0) {return false;}
		for(FormSet entry : getFormsetList()) {
			if(getName().equals(entry.getformsetName())) {
				return true;
			}
		}
		return false;
	}
	
}