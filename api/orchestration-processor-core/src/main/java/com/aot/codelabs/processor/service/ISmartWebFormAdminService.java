package com.aot.codelabs.processor.service;

import java.util.List;

import com.aot.codelabs.processor.model.SmartWebFormProcess;
import com.aot.codelabs.processor.model.FormSet;
import com.aot.codelabs.processor.model.WebForm;
import com.aot.codelabs.processor.model.WorkProcess;

/**
 * Interface for admin operations
 * 
 * @author Sumathi Thirumani
 */
public interface ISmartWebFormAdminService {
	
	//FormSet operations
	List<FormSet> getAllFormSet();
	FormSet getFormSetById(Integer groupId);
	void saveFormSet(FormSet formSet);
	
	//Form operations
	List<WebForm> getAllForms();
	WebForm getWebFormById(Integer formId);
	void saveWebForm(WebForm form);
	
	//Process Operations
	List<WorkProcess> getAllProcess();
	WorkProcess getProcessById(Integer processId);
	void saveWorkProcess(WorkProcess process);
	
	//Mapper Associations
	List<SmartWebFormProcess> getAllWebFormProcess();
	void saveWebFormProcess(SmartWebFormProcess process);
	SmartWebFormProcess getWebFormProcess(Integer mapId);
}