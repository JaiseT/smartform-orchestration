package com.aot.codelabs.accelerator.controller.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;

import com.aot.codelabs.accelerator.controller.IAdminController;
import com.aot.codelabs.processor.model.SmartWebFormProcess;
import com.aot.codelabs.processor.model.FormSet;
import com.aot.codelabs.processor.model.WebForm;
import com.aot.codelabs.processor.model.WorkProcess;
import com.aot.codelabs.processor.service.impl.SmartWebFormAdminService;

/**
 * Implementation class for admin operations.
 * 
 * @author Sumathi Thirumani
 */
@RestController
public class AdminController implements IAdminController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	@Qualifier("smartWebFormAdminService")
	private SmartWebFormAdminService smartWebFormAdminService;
	
	@Override
	public List<FormSet> getAllFormSet(){
		logger.debug("Fetch all formset");
		List<FormSet> ref = new ArrayList<FormSet>();
		ref= getSmartWebFormAdminService().getAllFormSet();
		return ref;
	}
	
	@Override
	public FormSet getFormSet(Integer formsetId){
		return getSmartWebFormAdminService().getFormSetById(formsetId);
	}
	
	@Override
	public void saveFormSet(FormSet formset) {
		logger.debug("Persist formset information");
		smartWebFormAdminService.saveFormSet(formset);
	}

	@Override
	public List<WebForm> getAllForm() {
		return smartWebFormAdminService.getAllForms();
	}

	@Override
	public WebForm getForm(Integer formId) {
		return smartWebFormAdminService.getWebFormById(formId);
	}

	@Override
	public void saveForm(WebForm form) {
		smartWebFormAdminService.saveWebForm(form);
	}
	
	@Override
	public List<WorkProcess> getAllProcess() {
		return getSmartWebFormAdminService().getAllProcess();
	}
	
	@Override
	public WorkProcess getWorkProcess(Integer processId) {
		return getSmartWebFormAdminService().getProcessById(processId);
	}
	
	@Override
	public List<SmartWebFormProcess> getAllActiveFormProcess(){
		logger.debug("Fetch all active process for orchestration");
		return getSmartWebFormAdminService().getAllWebFormProcess();
	}
	
	@Override
	public void saveFormProcess(SmartWebFormProcess process) {
		getSmartWebFormAdminService().saveWebFormProcess(process);
	}
	
	public SmartWebFormAdminService getSmartWebFormAdminService() {
		return smartWebFormAdminService;
	}

	public void setSmartWebFormAdminService(SmartWebFormAdminService smartWebFormAdminService) {
		this.smartWebFormAdminService = smartWebFormAdminService;
	}

	@Override
	public void saveWorkProcess(WorkProcess process) {
		smartWebFormAdminService.saveWorkProcess(process);
	}

	@Override
	public SmartWebFormProcess getFormProcess(Integer mapId) {
		return smartWebFormAdminService.getWebFormProcess(mapId);
	}

}