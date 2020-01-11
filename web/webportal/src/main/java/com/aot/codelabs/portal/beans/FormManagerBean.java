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

import com.aot.codelabs.forms.support.orbeon.FormMetaData;
import com.aot.codelabs.processor.model.FormSet;
import com.aot.codelabs.processor.model.WebForm;

/**
 * Managed bean for handling user forms.
 * 
 * @author Sumathi Thirumani
 */
@Component
@ManagedBean("formmanager")
@ViewScoped
public class FormManagerBean extends BaseWebPortalBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(FormManagerBean.class);
	
	private List<FormSet> formsetList;
	private List<FormMetaData> formMetaDataList;
	private List<WebForm> formList;
	
	private String selectedFormset;
	private String selectedForm;
	private String description;
	private String status;
	private String comments;
	private Integer id  = 0;
	
	public void loadConfiguration() {
		fetchFormSet();
		prepareSmartFormList();
	}
	
	private void fetchFormSet() {
		formsetList = new ArrayList<FormSet>();
		FormSet[] formsetArr = getRestTemplate().getForObject(getServiceURI()+"/admin/formset", FormSet[].class);
		setFormsetList(Arrays.asList(formsetArr));
		if(CollectionUtils.isEmpty(formsetList)) {
			logger.error("Error occured in fetching form set.");
		}
	}
	
	public void fetchOrbeonForms(ValueChangeEvent event) {
		formMetaDataList = new ArrayList<FormMetaData>();
		String inp = (String) event.getNewValue();
		if(StringUtils.isEmpty(inp) || "NA".equals(inp)) {
			logger.info("Formset value cannot be empty.");
			return;
		}
		logger.debug(getFormServiceURI()+"/service/persistence/form/"+getValue(inp).toLowerCase());
		FormMetaData[] forms = getRestTemplate().getForObject(getFormServiceURI()+"/service/persistence/form/"+getValue(inp).toLowerCase(), FormMetaData[].class);
		setFormMetaDataList(Arrays.asList(forms));
		if(CollectionUtils.isEmpty(formMetaDataList)) {
			logger.error("Error occured in fetching forms.");
		}
	}
	
	private void prepareSmartFormList() {
		if(CollectionUtils.isEmpty(getFormsetList())) {return;}
		formList = new ArrayList<WebForm>();
		for(FormSet entry : getFormsetList()) {
			if(!CollectionUtils.isEmpty(entry.getForms())) {
				getFormList().addAll(entry.getForms());
			}
		}
	}
	
	public void reset(){
		setSelectedFormset(null);
		setSelectedForm(null);
		setDescription(null);
		setStatus(null);
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
		
		FormSet insObj = getFormSet(getValue(getSelectedFormset()));
		List<WebForm> formList = new ArrayList<WebForm>();
		//prepare form object
		WebForm form = new WebForm();
		FormMetaData metaObj = getForm(getValue(getSelectedForm()));
		form.setFormName(metaObj.getFormName());
		form.setFormDescription(getDescription());
		form.setFormSource(metaObj.getSource());
		form.setFormLocation(getFormServiceURI()+"/"+insObj.getformsetName().toLowerCase()+"/"+metaObj.getFormName()+"/new");
		form.setFormSet(insObj);
		form.setStatus(getStatus());
		form.setComments(getComments());
		form.setCreatedBy(getUserName());
		form.setUpdatedBy(getUserName());
		if(getId() > 0) { form.setFormId(getId());}
		formList.add(form);
		insObj.setForms(formList);
		try {
		 logger.debug("Association of form instance {}", insObj.toString());
		 getRestTemplate().postForObject(getServiceURI()+"/admin/formset/save", insObj, Void.class);
		 logger.info("Association of form instance successful for {}", insObj.toString());
		 reset();
		 msgs.add("Data has been submitted successfully.");
		 getMsgList().put("SUCCESS",msgs);
		 } catch(RuntimeException ex) {
			 msgs.add("System Error Occured.");
			 getMsgList().put("FAILURE", msgs);
			// throw new RuntimeException("Exception occured in persisting formset data.");
		 }
	}
	
	public void populate(WebForm form) {
		logger.debug("Populate SmartForm");
		setSelectedFormset(form.getFormset().getformsetId()+"_"+form.getFormset().getformsetName());
		setSelectedForm(form.getFormSource()+"_"+form.getFormName());
		setDescription(form.getFormDescription());
		setStatus(form.getStatus());
		setComments(form.getComments());
		setId(form.getFormId());
		setMsgList(new HashMap<String,List<String>>());
	}
	
	private FormSet getFormSet(String formsetName) {
		for(FormSet entry: getFormsetList()) {
			if(formsetName.equals(entry.getformsetName())) {
				return entry;
			}
		}
		logger.error("No data found for formset name: "+formsetName);
		return null;
	}
	
	private FormMetaData getForm(String formName) {
		for(FormMetaData entry: getFormMetaDataList()) {
			if(formName.equals(entry.getFormName())) {
				return entry;
			}
		}
		return null;
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

	public List<FormMetaData> getFormMetaDataList() {
		return formMetaDataList;
	}

	public void setFormMetaDataList(List<FormMetaData> formMetaDataList) {
		this.formMetaDataList = formMetaDataList;
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
		if(StringUtils.isEmpty(getSelectedFormset()) || "NA".equals(getSelectedFormset())) {errMsgs.add("Formset cannot be Empty.");}
		if(StringUtils.isEmpty(getSelectedForm()) || "NA".equals(getSelectedForm())) {errMsgs.add("Form cannot be Empty.");}
		if(StringUtils.isEmpty(getStatus()) || "NA".equals(getStatus())) {errMsgs.add("Status cannot be Empty.");}
		
		Boolean isExists = isDuplicate();
		if(isExists) {errMsgs.add("Record already exists.");}
		return errMsgs;
	}
	
	private Boolean isDuplicate() {
		if(getId() > 0) {return false;}
		for(WebForm entry : getFormList()) {
			if(getValue(getSelectedForm()).equals(entry.getFormSource()+"_"+entry.getFormName())) {
				return true;
			}
		}
		return false;
	}

}